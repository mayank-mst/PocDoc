package com.example.priyanka.mapsdemo;

public class medications extends MainActivity {

    String d;

    public medications(String d) {
        this.d = d;
    }


    public String print_medications() {

        switch (d)
        {
            case "cold":
                return "These are the medications that u may try:" +
                        "\nNumber 1 Take XYZAL tablet. \nNumber 2 wash up your affected area. \nNumber 3 Try using eye drops and nasal sprays. \nNumber 4  You might be allergic to pollens try using air conditioner instead of opening your windows";

            case "anaemia":
                return  "These are the medications that u may try:" +
                        "\nNumber 1 Take iron supplements for improving your iron deficiency. \nNumber 2 Try including iron rich diet like pulses-beans-brown rice-white and red meats.  \nNumber 3 If severe please consider a doctor.";

            case "diarrhea":
                return "These are the medications that u may try:" +
                        "\nNumber 1 Drink plenty of fluids. \nNumber 2 Follow \"BRAT\" diet(Banana-Rice-AppleSauce-Toast). \nNumber 3 Avoid milk & dairy products. \nNumber 4 Avoid spicy foods";

            case "AIDS":
                return
                        "Im so sorry we do not have any home remedies please consult a doctor ASAP.";


            case "diabeties":
                return "These are the medications that u may try:" +
                        "\nNumber 1 Exercise regularly. \nNumber 2 Increase your fibre intake. \nNumber 3 Control your carb intake. \nNumber 4 Drink water and stay Hyderated. \nNumber 5 Get enough quality sleep,Try to take sugar free foods";






        }

        return "nothing";

    }
}
