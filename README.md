# API


Base URL = https://docme1.p.rapidapi.com


**Patient**
  - get patient: /patient/`patient_id`, `patient_id` - String, @GET, Response: { "id": "`patient_id`"} on success, {"detail": "Not Found"} else
  - new patient: /patient, @POST, Body: {}, Response: { "id": "`patient_id`"} on success

 **Measurement**
   - get measurement: /patient/`patient_id`/measurement/`measurement_id`, `measurement_id` - String, @GET, Response: {"id":"`measurement_id`", "status":"SUCCESS", "timestamp":`timestamp`} on success, {"id":"`measurement_id`", "status":"ERROR" "timestamp":`timestapmp` "error_details":"internal server error"} on server error, {"detail": "Not Found"} else, `timestamp` - Long - current time in seconds
   - new measurement: /patient/`patient_id`/measurement, @POST, Body: {"video": `video`, "timestamp": `timestamp`}, `video` - Byte array (*Base64?*), Response: {"id":"`measurement_id`", "status":"PROCESSING", "timestamp":`timestamp`} on success
 
 **HM3**
   - get HM3 for a patient: /patient/`patient_id`/hm3, @GET, Response: {"state":"`state`", "message":"`message`"}, `state`, `message` - String
 
 Test API: https://rapidapi.com/perfexia-health-perfexia-health-default/api/docme1/
