package com.carparking.project.service;

import com.carparking.project.domain.*;
import com.carparking.project.entities.Profile;
import com.carparking.project.entities.Rates;
import com.carparking.project.entities.Slots;
import com.carparking.project.entities.User;
import com.carparking.project.helper.JotFormSubmissionsHelper;
import com.carparking.project.helper.SlotsHelper;
import com.carparking.project.repository.LoginRepository;
import com.carparking.project.repository.ProfileRepository;
import com.carparking.project.repository.SlotsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProfileService {

    @Autowired
    SlotsHelper slotsHelper;

    @Autowired
    JotFormSubmissionsHelper jotFormSubmissionsHelper;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    JotFormSubmissionsHelper jotFormSubmissions;

    @Autowired
    SlotsRepository slotsRepository;

    @Autowired
    RatesService ratesService;

    @Autowired
    LoginService loginService;

    @Autowired
    ObjectMapper objectMapper;


    public static LocalDateTime addCustomDuration(LocalDateTime dateTime, String durationStr) {
        Pattern pattern = Pattern.compile("(\\d+)([hms])"); // Regex to extract hours, minutes, seconds
        Matcher matcher = pattern.matcher(durationStr);

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "h":
                    dateTime = dateTime.plus(value, ChronoUnit.HOURS);
                    break;
                case "m":
                    dateTime = dateTime.plus(value, ChronoUnit.MINUTES);
                    break;
                case "s":
                    dateTime = dateTime.plus(value, ChronoUnit.SECONDS);
                    break;
            }
        }
        return dateTime;
    }

    //for app users
    public String saveProfile(ProfileDto profileDto) throws Exception {
        Profile entity = new Profile();
        entity.setVehicleNumber(profileDto.getVehicleNumber());
        entity.setUserName(profileDto.getUserName());
        entity.setUserEmailId(profileDto.getUserEmailId());
        entity.setVehicleModel(profileDto.getVehicleModel());
        entity.setVehicleBrand(profileDto.getVehicleBrand());
        entity.setVehicleClr(profileDto.getVehicleClr());
        entity.setFuelType(profileDto.getFuelType());
        entity.setVehicleGene(profileDto.getVehicleGene());
        entity.setVehicleType(profileDto.getVehicleType());
        entity.setPhoneNum(profileDto.getPhoneNum());
        entity.setNoOfVehicles(1);
        entity.setPaidStatus(false);
        entity.setPaidAmount((double) 0);
        entity.setTotalAmount((double) 0);
        entity.setBanned(false);
        entity.setFineAmount((double) 0);


        UserDto userDto = new UserDto();
        userDto.setEmail(profileDto.getUserEmailId());
        userDto.setPassword(profileDto.getPassword());
        userDto.setRoleName("USER");
        userDto.setActive("INACTIVE");

        User user = loginRepository.save(new User(userDto));
        Profile profile = profileRepository.save(entity);
        if (Objects.nonNull(user) && Objects.nonNull(profile)) {
            return "User Is Created";
        } else {
            throw new Exception("User Creation Failed");
        }
    }

    //for on-site users
    public void saveOnSiteProfile(String vehicleNumber, Slots slots, String bookingSource) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime currentTime = LocalDateTime.now();
        String formattedDateTime = currentTime.format(formatter);

        LocalDateTime exitDateTime = currentTime.plusHours(1);
        String exitTime = exitDateTime.format(formatter);

        LocalDateTime startTime = LocalDateTime.now();

        LocalDateTime endTime = startTime.plusHours(1);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("hh:mm a");

        String formattedStartTime = startTime.format(formatter1);
        String formattedEndTime = endTime.format(formatter1);

        String duration = formattedStartTime + " - " + formattedEndTime;
        long difference = ChronoUnit.MINUTES.between(startTime, endTime);

        Rates rates = ratesService.getRates(slots.getAdminMailId());


        Profile profile = new Profile();
        profile.setVehicleNumber(vehicleNumber);
        profile.setVehicleType("car");
        profile.setBookingDate(formattedDateTime);
        profile.setPaidStatus(true);
        profile.setPaidAmount(rates.getCharge());
        profile.setAllocatedSlotNumber(slots.getSlotNumber());
        profile.setParkedPropertyName(slots.getPropertyName());
        profile.setDurationOfAllocation(String.valueOf(difference));
        profile.setPaymentDate(formattedDateTime);
        profile.setAdminMailId(slots.getAdminMailId());
        profile.setTotalAmount(rates.getCharge());
        profile.setBookingTime(duration);
        profile.setBookingSource(bookingSource);
        profile.setEndtime(exitTime);
        profile.setNoOfVehicles(1);
        profile.setBanned(false);
        profile.setFineAmount((double) 0);

        Profile obj = profileRepository.save(profile);
        if (Objects.nonNull(obj)) {
            System.out.println("On site profile created");
        } else {
            throw new Exception("On-Site profile creation failed");
        }
    }

    public List<Profile> getProfilesByAdminMailId(String adminMailId) {
        return profileRepository.findByAdminMailId(adminMailId);
    }

    public List<Profile> getProfileByUserEmail(String userEmailId) {
        return profileRepository.findByUserEmailId(userEmailId);
    }

    public Profile getProfileByVehicleNumber(String vehicleNumber) {
        return profileRepository.findByVehicleNumber(vehicleNumber);
    }

    public List<Profile> getProfiles() {
        List<Profile> list = new ArrayList<>();
        profileRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public String updateProfile(String adminMailId) throws Exception {


        Slots slots = slotsRepository.findByAdminMailId(adminMailId)
                .stream()
                .findFirst()
                .orElse(null);

        String sheetId = Objects.requireNonNull(slots).getSheetId();

        System.out.println(sheetId);

        String jsonResponse = jotFormSubmissionsHelper.getFormResponse(sheetId);
        JsonResponse jsonResponse1 = objectMapper.readValue(jsonResponse, JsonResponse.class);
        List<FormContent> formdata = jsonResponse1.getContent();

        if (formdata.isEmpty()) {
            throw new Exception("No form submissions found");
        }

        Map<String, Answer> answers = formdata.stream().map(form -> form.getAnswers()).findFirst().get();

        System.out.println("Available Answer Keys:");
        answers.keySet().forEach(System.out::println);

        // Use the field IDs we discovered from the log
        Answer userName = answers.get("17");       // User Name
        Answer email = answers.get("4");           // Email
        Answer phoneNumber = answers.get("30");    // Phone Number
        Answer vehicleNumberField = answers.get("29"); // Vehicle Number
        Answer vehicleType = answers.get("18");    // Type of vehicle
        Answer vehicleGene = answers.get("19");    // Vehicle Gene
        Answer vehicleModel = answers.get("21");   // Vehicle Model
        Answer vehicleBrand = answers.get("20");   // Vehicle Brand
        Answer fuelType = answers.get("22");       // Fuel Type
        Answer vehicleColor = answers.get("24");   // Vehicle Color
        Answer slotNumber = answers.get("31");     // Slot Number

        String userNameValue = userName.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");
        String emailValue = email.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");
        String phoneNumberValue = phoneNumber.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");
        String vehicleNumberValue = vehicleNumberField.getAnswer().toString().replaceAll("\\s", "").replace("\"", "").toLowerCase();
        String vehicleTypeValue = vehicleType.getAnswer().toString().replaceAll("\\s", "").replace("\"", "").toLowerCase();
        String vehicleGeneValue = vehicleGene.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");
        String vehicleModelValue = vehicleModel.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");
        String vehicleBrandValue = vehicleBrand.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");
        String fuelTypeValue = fuelType.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");
        String vehicleColorValue = vehicleColor.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");
        String slotNumberValue = slotNumber.getAnswer().toString().replaceAll("\\s", "").replace("\"", "");

        System.out.println(userNameValue);
        System.out.println(emailValue);
        System.out.println(phoneNumberValue);
        System.out.println(vehicleNumberValue);
        System.out.println(vehicleTypeValue);
        System.out.println(vehicleGeneValue);
        System.out.println(vehicleModelValue);
        System.out.println(vehicleBrandValue);
        System.out.println(fuelTypeValue);
        System.out.println(vehicleColorValue);
        System.out.println(slotNumberValue);

        try {
            Profile profile = profileRepository.findByVehicleNumber(vehicleNumberValue);
            profile.setPhoneNum(phoneNumberValue);
            profile.setUserName(userNameValue);
            profile.setUserEmailId(emailValue);
            profile.setVehicleModel(vehicleModelValue);
            profile.setVehicleBrand(vehicleBrandValue);
            profile.setFuelType(fuelTypeValue);
            profile.setVehicleClr(vehicleColorValue);
            profile.setVehicleGene(vehicleGeneValue);

            profileRepository.save(profile);

            UserDto userDto = new UserDto();
            userDto.setEmail(emailValue);
            userDto.setRoleName("USER");
            userDto.setPassword(userNameValue + "@" + (int) (Math.random() * 10000));
            loginService.signUp(userDto, "USER");


        } catch (Exception e) {
            System.out.println("e===" + e.getMessage());
        }
        return "Successfully completed";
    }

    public Map<String, String> getAllTimer() {
        LocalDateTime currentDate = LocalDateTime.now();
        Map<String, String> result = null;
        List<Profile> profiles = profileRepository.findAll().stream().filter(p -> p.getAdminMailId().equals("gokulgnair777@gmail.com")).collect(Collectors.toList());
        try {
            result = profiles.stream()
                    .collect(Collectors.toMap(Profile::getVehicleNumber, p -> duration(currentDate, convertToLocalDateTime(LocalDateTime.parse(p.getEndtime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")).toLocalTime(), currentDate.toLocalDate()))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private LocalDateTime convertToLocalDateTime(LocalTime time, LocalDate date) {
        return LocalDateTime.of(date, time);
    }

    public String duration(LocalDateTime currentDateTime, LocalDateTime endDateTime) {
        Duration remainingDuration = Duration.between(currentDateTime, endDateTime);
        long hours = remainingDuration.toHours();
        long minutes = remainingDuration.toMinutes() % 60;
        long seconds = remainingDuration.getSeconds() % 60;
        System.out.println(String.format("%02d hours, %02d minutes, %02d seconds", hours, minutes, seconds));
        String output = String.format("%02d hours, %02d minutes, %02d seconds", hours, minutes, seconds);
        return output;
    }

    public String ban(String vehicleNumber) {
        Profile profile = profileRepository.findByVehicleNumber(vehicleNumber);
        User user = loginRepository.findByEmail(profile.getUserEmailId());
        profile.setBanned(true);
        user.setBanned(true);
        profile = profileRepository.save(profile);
        if (Objects.nonNull(profile)) {
            return "banned";
        }
        return "failed";
    }

    public String rechargeProfile(String vehiclenumber, String duration, Double fare) {
        try {
            Profile profile = profileRepository.findByVehicleNumber(vehiclenumber);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(profile.getEndtime(), formatter);
            LocalDateTime newDateTime = addCustomDuration(dateTime, duration);

            String formattedDateTime = newDateTime.format(formatter);

            //  LocalDateTime startTime = LocalDateTime.parse(profile.getBookingDate(), formatter);
            // LocalDateTime endTime = LocalDateTime.parse(formattedDateTime);

            //long difference = ChronoUnit.MINUTES.between(startTime, endTime);

            profile.setEndtime(formattedDateTime);
            profile.setPaidAmount(profile.getPaidAmount() + fare);
            profile.setTotalAmount(profile.getPaidAmount() + profile.getTotalAmount());
            //profile.setDurationOfAllocation(String.valueOf(difference));
            profileRepository.save(profile);

            Slots slots = slotsRepository.findByVehicleNum(vehiclenumber);
            slots.setExitTime(formattedDateTime);
            slotsRepository.save(slots);
            return "";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String leaveSlotFlow(String slotnumber) {
        Profile profile = profileRepository.findByAllocatedSlotNumber(slotnumber);
        Slots slots = slotsRepository.findBySlotNumber(slotnumber);
        slotsHelper.leaveSlot(slotsRepository.findBySlotNumber(slotnumber));
        profile.setAllocatedSlotNumber("");
        profile.setEndtime(String.valueOf(0));
        profileRepository.save(profile);
        JSONArray submissions = jotFormSubmissionsHelper.getSubmissionId(slots.getSheetId());
        for (int i = 0; i < submissions.length(); i++) {
            String submissionId = submissions.getJSONObject(i).getString("id");
            jotFormSubmissionsHelper.deleteForm(submissionId);
        }
        return slotnumber + "left";
    }

}
