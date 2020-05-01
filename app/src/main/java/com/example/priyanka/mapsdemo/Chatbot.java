package com.example.priyanka.mapsdemo;

// corresponds to main act of BOT

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Chatbot extends AppCompatActivity {

    EditText userInput;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    public  List<ResponseMessage> responseMessageList;
    TextToSpeech t1;
    public boolean turn1=true,turn2=false,complete=true,just_finished=false,chat_begins=true,medi=false,medi_just_finished=false;
    String name="";

    public int check =1,common=0,wrong=0,done=0;

    TimeUnit time = TimeUnit.SECONDS;


    String disease="";

    public void start1() throws Exception
    {
        ResponseMessage responseMessage1 = new ResponseMessage("Hi, I am your Medicobot. Feeling excited to help u :)", false);
        responseMessageList.add(responseMessage1);


        String str = "Hi, I am your Medicobot. Feeling excited to help u :)";
        String toSpeak = str.toLowerCase();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);




        responseMessage1 = new ResponseMessage("What is your name ?", false);
        responseMessageList.add(responseMessage1);


        str = "What is your name ?";
        toSpeak = str.toLowerCase();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void start2() throws  Exception
    {
        ResponseMessage responseMessage1 = new ResponseMessage("Hi "+name +"!!!!. How can i help u ?", false);
        responseMessageList.add(responseMessage1);


        String str = "Hi"+name +"!!!!. How can i help u ?";
        String toSpeak = str.toLowerCase();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot);

        userInput=findViewById(R.id.userInput);
        recyclerView=findViewById(R.id.conversation);
        responseMessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(responseMessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        try {
            start1();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener()  {

                                                int t=0,start=0;
                                                String sym_list ="";
                                                @Override
                                                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)  {
                                                    if (i == EditorInfo.IME_ACTION_SEND) {

                                                        ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true);
                                                        responseMessageList.add(responseMessage);

                                                        if(start==0) {
                                                            name = userInput.getText().toString();

                                                        }

                                                        try {
                                                            if(start==0)
                                                                start2();
                                                        }

                                                        catch(Exception e)
                                                        {
                                                            e.printStackTrace();
                                                        }

                                                        ResponseMessage responseMessage1 = new ResponseMessage("dummy",false);

                                                        if(medi)
                                                        {
                                                            medications m = new medications(disease);
                                                            String me=m.print_medications();
                                                            ResponseMessage r = new ResponseMessage("Ok "+name+" \n"+ me,false);
                                                            responseMessageList.add(r);

                                                            String toSpeak =("Ok "+name+" \n"+ me).toLowerCase();
                                                            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                            medi=false;

                                                            medi_just_finished=true;
                                                        }

                                                        if(check==0 && complete==false) {
                                                            String resp = userInput.getText().toString();
                                                            String sym;

                                                            if (turn1) {

                                                                if (!(resp.contains("no"))|| ((resp.contains("No")) || resp.contains("nothing") || resp.contains("thats it") || resp.contains("thats all") || resp.contains("done"))) {
                                                                    if (t % 4 == 0) {
                                                                        responseMessage1 = new ResponseMessage(name+ " What is the symptom?", false);
                                                                        responseMessageList.add(responseMessage1);


                                                                        String str = name+ " What is the symptom?";
                                                                        String toSpeak = str.toLowerCase();
                                                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                    }

                                                                    if (t % 4 == 1) {
                                                                        responseMessage1 = new ResponseMessage("Could you mention the Symptom?", false);
                                                                        responseMessageList.add(responseMessage1);

                                                                        String str = "Could you mention the Symptom?";
                                                                        String toSpeak = str.toLowerCase();
                                                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                    }

                                                                    if (t % 4 == 2) {
                                                                        responseMessage1 = new ResponseMessage(name+" May i know the symptom?", false);
                                                                        responseMessageList.add(responseMessage1);

                                                                        String str = name+" May i know the symptom?";
                                                                        String toSpeak = str.toLowerCase();
                                                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                    }

                                                                    if (t % 4 == 3) {
                                                                        responseMessage1 = new ResponseMessage("Ohh okay what was the symptom "+name+" ?", false);
                                                                        responseMessageList.add(responseMessage1);

                                                                        String str = "Ohh okay what was the symptom "+name+" ?";
                                                                        String toSpeak = str.toLowerCase();

                                                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                    }

                                                                    t++;
                                                                }

                                                                else {

                                                                    check = 1;
                                                                    complete=true;
                                                                    just_finished=true;
                                                                }

                                                                turn2=true;
                                                                turn1=false;
                                                            }


                                                            else
                                                            {
                                                                sym=userInput.getText().toString();

                                                                if(sym.contains("cough") || sym.contains("sneeze") || sym.contains("fever"))
                                                                {
                                                                    common=1;
                                                                    responseMessage1 = new ResponseMessage("These are more common symptoms could you be more specific. Do u have any other symptoms other than these (YES/NO) ?", false);
                                                                    responseMessageList.add(responseMessage1);

                                                                    String str = "These are more common symptoms could you be more specific. Do u have any other symptoms other than these (YES/NO) ?";
                                                                    String toSpeak = str.toLowerCase();


                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


                                                                }

                                                                sym_list=sym_list.concat(" "+sym);





                                                                if(common==0) {



                                                                    responseMessage1 = new ResponseMessage("Any additional symptom faced (YES or NO)?", false);
                                                                    responseMessageList.add(responseMessage1);

                                                                    String str = "Any additional symptom faced Yes of no ?";
                                                                    String toSpeak = str.toLowerCase();


                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);






                                                                }

                                                                else
                                                                    common=0;


                                                                turn1=true;
                                                                turn2=false;




                                                            }


                                                        }

                                                        TensorFlowInferenceInterface tensorFlowInferenceInterface;

                                                        tensorFlowInferenceInterface = new  TensorFlowInferenceInterface(getAssets(),"cm.pb");




                                                        if(check!=0 && complete) {

                                                            float[] res = {0,0,0,0,0,0,0,0,0,0};

                                                            float[] bag = new float[87];
                                                            Random rand = new Random();

                                                            // String[] labels = {"goodbye", "greeting", "headache", "opentoday", "payments", "thanks"};

                                                            String[] words = new String[]{"abdominal", "aches", "age", "alright", "anyone", "body", "bowel", "breath", "bye", "call", "called",
                                                                    "chills", "cold", "congestion", "cough", "cramping", "decreased", "dehydration", "develop", "diagnosis", "drip", "dude", "energy",
                                                                    "eyes", "fatigue", "feel", "fever", "feverish", "flu", "good", "goodbye", "headache", "headaches", "heart", "hello", "help", "hey",
                                                                    "hi", "hunger", "inflammation", "itchy", "joint", "later", "lightheaded", "lymph", "morning", "mouth", "movements", "muscle", "name",
                                                                    "nasal", "need", "nice", "nodes", "nose", "old", "pale", "palpitations", "pleasure", "postnasal", "problem", "rapid", "rate", "runny",
                                                                    "scratchy", "see", "shortness", "sneeze", "sneezing", "soon", "sore", "stuffy", "suggestion", "sweating", "swollen", "talking",
                                                                    "there", "thirst", "throat", "tired", "ulcers", "up", "urination", "wassup", "watery", "weakness", "whats"};

                                                            int j;
                                                            for (j = 0; j < words.length; j++) {

                                                                StringTokenizer str1;

                                                                if(!just_finished)
                                                                    str1 = new StringTokenizer(responseMessage.getText(), " ");
                                                                else
                                                                    str1 = new StringTokenizer(sym_list, " ");


                                                                while (str1.hasMoreTokens()) {


                                                                    String str = new String(str1.nextToken());


                                                                    if (str.equalsIgnoreCase(words[j]))

                                                                        bag[j] = (float) 1.0;
                                                                }
                                                            }

                                                            tensorFlowInferenceInterface.feed("my_input/X", bag, 1, words.length);
                                                            tensorFlowInferenceInterface.run(new String[]{"my_outpu/Softmax"});
                                                            tensorFlowInferenceInterface.fetch("my_outpu/Softmax", res);

                                                            float max = res[0];
                                                            int index = 0;
                                                            for (j = 0; j < 10; j++) {
                                                                if (res[j] > max) {
                                                                    max = res[j];
                                                                    index = j;
                                                                }
                                                            }


                                                            if (max < 0.8) {



                                                                if(just_finished) {

                                                                    if(wrong<2) {
                                                                        responseMessage1 = new ResponseMessage("I cannot predict with your inputs please try to provide more information  ", false);
                                                                        responseMessageList.add(responseMessage1);

                                                                        String str = "I cannot predict with your inputs pls try to provide more information so that i could predict the disease ";
                                                                        check = 0;
                                                                        complete = false;
                                                                        String toSpeak = str.toLowerCase();
                                                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                        responseMessage1 = new ResponseMessage("Have you faced other symptoms apart from what u have provided (YES or NO) ", false);
                                                                        responseMessageList.add(responseMessage1);

                                                                        str = "Have you faced other symptoms apart from what u have provided (YES or NO) ";
                                                                        check = 0;
                                                                        complete = false;
                                                                        toSpeak = str.toLowerCase();
                                                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                        turn1 = true;
                                                                        turn2 = false;
                                                                        complete = false;
                                                                        just_finished = false;
                                                                        chat_begins = true;
                                                                        check = 0;
                                                                        common = 0;

                                                                        wrong++;
                                                                    }

                                                                    else {
                                                                        responseMessage1 = new ResponseMessage("I am learning as of now i cannot predict the disease based on your inputs. Please do mention your thoughts about this issue in feedback session", false);
                                                                        responseMessageList.add(responseMessage1);

                                                                        String str = "I am learning as of now i cannot predict the disease based on your inputs. Please do mention your thoughts about this issue in feedback session ";
                                                                        check = 1;
                                                                        complete = true;
                                                                        turn1 = true;
                                                                        String toSpeak = str.toLowerCase();
                                                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                    }
                                                                }

                                                                else
                                                                {
                                                                    String resi;

                                                                    if(!(medi_just_finished) && start==1 ) {

                                                                        if(responseMessage.getText().contains("thank") || responseMessage.getText().contains("Thank"))
                                                                        resi="Always a pleasure to help you ";

                                                                        else
                                                                            resi="Cannot understand you ";

                                                                        responseMessage1 = new ResponseMessage(resi, false);
                                                                        responseMessageList.add(responseMessage1);

                                                                        String str = resi;
                                                                        String toSpeak = str.toLowerCase();
                                                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                        medi=false;                                }
                                                                }
                                                            }

                                                            else {


                                                                if (index == 1) {

                                                                    int rand_int1 = rand.nextInt(4);

                                                                    ArrayList<String> responses = new ArrayList<String>();

                                                                    responses.add("Hi i am 2 months old");
                                                                    responses.add("Existence ince Nov 2019");
                                                                    responses.add("My age is 2 months ");
                                                                    responses.add("Its been 2month since my entry to this world");


                                                                    responseMessage1 = new ResponseMessage(responses.get(rand_int1), false);
                                                                    responseMessageList.add(responseMessage1);

                                                                    String str = responses.get(rand_int1);
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


                                                                }

                                                                if (index == 5) {
                                                                    int rand_int1 = rand.nextInt(5);

                                                                    ArrayList<String> responses = new ArrayList<String>();

                                                                    responses.add("Okay see u later");
                                                                    responses.add("Good bye!!!!");
                                                                    responses.add("Will miss u ");
                                                                    responses.add("Always there for u!");
                                                                    responses.add("Have a nice day");


                                                                    responseMessage1 = new ResponseMessage(responses.get(rand_int1), false);
                                                                    responseMessageList.add(responseMessage1);

                                                                    String str = responses.get(rand_int1);
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                }
                                                                if (index == 7) {
                                                                    int rand_int1 = rand.nextInt(4);

                                                                    ArrayList<String> responses = new ArrayList<String>();

                                                                    responses.add("Good to see u again");
                                                                    responses.add("Welcome dude");
                                                                    responses.add("Happy to see u ");
                                                                    responses.add("Whats the problem");


                                                                    responseMessage1 = new ResponseMessage(responses.get(rand_int1), false);
                                                                    responseMessageList.add(responseMessage1);

                                                                    String str = responses.get(rand_int1);
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                }
                                                                if (index == 9) {
                                                                    int rand_int1 = rand.nextInt(4);

                                                                    ArrayList<String> responses = new ArrayList<String>();

                                                                    responses.add("My name is Medicobot");
                                                                    responses.add("Medicobot is my name");
                                                                    responses.add("Developer call me Medicobot!!");
                                                                    responses.add("Call me Medicobot!");


                                                                    responseMessage1 = new ResponseMessage(responses.get(rand_int1), false);
                                                                    responseMessageList.add(responseMessage1);

                                                                    String str = responses.get(rand_int1);
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                                                }

                                                                if (index == 8) {
                                                                    int rand_int1 = rand.nextInt(3);

                                                                    ArrayList<String> responses = new ArrayList<String>();

                                                                    responses.add("Ohh so sorry about that have u faced any symptoms(YES or NO)");
                                                                    responses.add("Ohh okay did u experience any symptom(YES or NO)");

                                                                    responses.add("Sorry to hear it. Can u say me whether u faced a symptom(YES or NO)");


                                                                    responseMessage1 = new ResponseMessage(responses.get(rand_int1), false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    String str = responses.get(rand_int1);
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    check = 0;
                                                                    complete=false;


                                                                }

                                                                if (index == 3) {
                                                                    responseMessage1 = new ResponseMessage("As per your feed i predict that u may be suffering from allergy or cold", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    String str = "As per your feed i predict that u may be suffering from allergy or cold";
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    sym_list="";

                                                                    responseMessage1 = new ResponseMessage("Have you taken any medications? If so specify it", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    str = "Have you taken any medications? If so specify it";
                                                                    toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    disease="cold";

                                                                    medi=true;




                                                                }


                                                                if (index == 0) {
                                                                    responseMessage1 = new ResponseMessage("As per your feed i predict that u may be suffering from Diarrhea", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    String str = "As per your feed i predict that u may be suffering from Diarrhea";
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    sym_list="";

                                                                    responseMessage1 = new ResponseMessage("Have you taken any medications? If so specify it", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    str = "Have you taken any medications? If so specify it";
                                                                    toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    disease="diarrhea";

                                                                    medi=true;




                                                                }

                                                                if (index == 2) {
                                                                    responseMessage1 = new ResponseMessage("As per your feed i predict that u may be suffering from AIDS", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    String str = "As per your feed i predict that u may be suffering from AIDS";
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    sym_list="";

                                                                    responseMessage1 = new ResponseMessage("Have you taken any medications? If so specify it", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    str = "Have you taken any medications? If so specify it";
                                                                    toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    disease="AIDS";

                                                                    medi=true;




                                                                }


                                                                if (index == 4) {
                                                                    responseMessage1 = new ResponseMessage("As per your feed i predict that u may be suffering from anaemia", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    String str = "As per your feed i predict that u may be suffering from anaemia";
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    sym_list="";

                                                                    responseMessage1 = new ResponseMessage("Have you taken any medications? If so specify it", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    str = "Have you taken any medications? If so specify it";
                                                                    toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    disease="anaemia";

                                                                    medi=true;




                                                                }


                                                                if (index == 6) {
                                                                    responseMessage1 = new ResponseMessage("As per your feed i predict that u may be suffering from diabeties", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    String str = "As per your feed i predict that u may be suffering from diabeties";
                                                                    String toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    sym_list="";

                                                                    responseMessage1 = new ResponseMessage("Have you taken any medications? If so specify it", false);
                                                                    responseMessageList.add(responseMessage1);


                                                                    str = "Have you taken any medications? If so specify it";
                                                                    toSpeak = str.toLowerCase();
                                                                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                                                                    disease="diabeties";

                                                                    medi=true;




                                                                }


                                                            }


                                                            if(check==1) {


                                                                just_finished = false;
                                                                turn1 = true;
                                                            }

                                                            if(!medi)
                                                                medi_just_finished=false;

                                                            if(start==0)
                                                                start=1;
                                                        }

                                                        messageAdapter.notifyDataSetChanged();
                                                        if (!isLastVisible())
                                                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                                                    }
                                                    userInput.setText("");
                                                    return false;
                                                }

                                            }
        );
    }
    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.merchant_toolbar_medicobot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.merchant_toolbar_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Main3Activity.class));
        }
        if (item.getItemId() == R.id.merchant_toolbar_dp) {
            //FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, diseasepredictor.class));
        }

        if (item.getItemId() == R.id.merchant_toolbar_maps) {
            //FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MapsActivity.class));
        }

        if (item.getItemId() == R.id.merchant_toolbar_SOS) {
            Intent i = getPackageManager().getLaunchIntentForPackage("com.quickblox.sample.videochat.java");
            startActivity(i);
        }

        return false;
    }
}
