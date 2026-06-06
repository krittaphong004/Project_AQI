package com.example.project_aqi;

public class AQIUtils {

    public static String getStatus(int aqi){

        if(aqi <= 50){
            return "Good";
        }
        else if(aqi <= 100){
            return "Moderate";
        }
        else if(aqi <= 150){
            return "Unhealthy";
        }
        else{
            return "Dangerous";
        }
    }

    public static String getDanger(int aqi){

        if(aqi <= 50){
            return "อากาศดี ปลอดภัย";
        }
        else if(aqi <= 100){
            return "คุณภาพอากาศปานกลาง";
        }
        else if(aqi <= 150){
            return "เริ่มมีผลต่อสุขภาพ";
        }
        else{
            return "อันตรายต่อสุขภาพ";
        }
    }

    public static String getRecommendation(int aqi) {
        if (aqi <= 50) {
            return "อากาศดี สามารถทำกิจกรรมกลางแจ้งได้ตามปกติ";
        } else if (aqi <= 100) {
            return "ผู้ที่มีโรคทางเดินหายใจควรลดเวลาทำกิจกรรมกลางแจ้ง";
        } else if (aqi <= 150) {
            return "ควรสวมหน้ากากอนามัยเมื่อออกนอกอาคาร และลดกิจกรรมกลางแจ้ง";
        } else if (aqi <= 200) {
            return "หลีกเลี่ยงกิจกรรมกลางแจ้งเป็นเวลานาน ปิดประตูและหน้าต่าง";
        } else if (aqi <= 300) {
            return "ควรอยู่ภายในอาคาร ใช้เครื่องฟอกอากาศ และสวมหน้ากาก N95 หากจำเป็นต้องออกนอกบ้าน";
        } else {
            return "อันตรายมาก ควรหลีกเลี่ยงการออกจากอาคารโดยเด็ดขาด";
        }
    }
}