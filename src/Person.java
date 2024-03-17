public class Person {
    private String userName;
    private String userSurName;
    private String userEmail;

    public Person(String name,String surName, String email){
        this.userName = name;
        this.userSurName = surName;
        this.userEmail = email;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String name){
        this.userName = name;
    }
    public String getUserSurName(){
        return userSurName;
    }
    public void setUserSurName(String surName){
        this.userSurName = surName;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public void setUserEmail(String email) {
        this.userSurName = email;
    }


    public void printPersonalInfo(){
        System.out.println("Personal Information");
        System.out.println("Name: " + getUserName());
        System.out.println("Surname: " + getUserSurName());
        System.out.println("Email: " + getUserEmail());
        //can't i use any ways to print these info in tickets
    }
}

