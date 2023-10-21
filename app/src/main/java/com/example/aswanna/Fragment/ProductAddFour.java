package com.example.aswanna.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aswanna.Activities.ProposalAdd;
import com.example.aswanna.Model.Proposal;
import com.example.aswanna.R;
import com.example.aswanna.SendNotificationPack.APIService;
import com.example.aswanna.SendNotificationPack.Client;
import com.example.aswanna.SendNotificationPack.Data;
import com.example.aswanna.SendNotificationPack.MyResponse;
import com.example.aswanna.SendNotificationPack.NotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductAddFour extends Fragment {

    private StorageReference storageReference;

    private ImageView img;

    private TextView proposalId,projectName;

    private String data1,data2,data3,data4,data5,data6,data7,downloadUrl1,downloadUrl2,PID;


    private APIService apiService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        View view=inflater.inflate(R.layout.fragment_product_add_four, container, false);

        apiService=  Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        ProposalAdd proposalAdd=(ProposalAdd) getActivity();

        ImageView imageView = proposalAdd.findViewById(R.id.four);
        Drawable drawable1 = ContextCompat.getDrawable(requireContext(), R.drawable.numfourw);
        Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.correct);

        imageView.setImageDrawable(drawable1);


        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference proposalsCollection = firestore.collection("proposals");
        img=view.findViewById(R.id.proposalImage);
        proposalId=view.findViewById(R.id.propsalid);
        projectName=view.findViewById(R.id.productname);

        Random random = new Random();


        int lastTwoDigits = random.nextInt(100);

        // Combine with the first two digits "00"
        String PID = String.format("00%02d", lastTwoDigits);

        Bundle args = getArguments();

        if (args != null) {
            data1 = args.getString("pName");
            data2=args.getString("location");
            data3=args.getString("type");
            data4=args.getString("time");
            data5=args.getString("description");
            data6=args.getString("expected");
            data7=args.getString("fund");
            downloadUrl1=args.getString("imgUrlOne");
            downloadUrl2=args.getString("imgUrlTwo");

        }


          projectName.setText(data1);
       proposalId.setText("Proposal ID-"+PID);


         // Change the path to your image




        Glide.with(this).load(downloadUrl1).into(img);






        Button btn=proposalAdd.findViewById(R.id.nextButton);

        btn.setText("Pay & Submit");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code to display a Toast message here.
                String farmerId="0002";
                String status="on";
                String documentId = proposalsCollection.document().getId();

                Date currentDate = new Date();

                // Define the desired date format
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

                // Format the date as a string
                String postedDate = sdf.format(currentDate);



                Proposal proposal = new Proposal(PID,documentId,farmerId,data1,data3,data2,data4,data5,data7,data6,downloadUrl1,downloadUrl2,status,postedDate);


                proposalsCollection.document(documentId).set(proposal)
                        .addOnSuccessListener(aVoid -> {


                            imageView.setImageDrawable(drawable);
                            ProductAddFive productAddFive = new ProductAddFive();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewPager,productAddFive,null).addToBackStack(null).commit();
                            getUsersTokensFromFirestore(data1,data2);

                        })
                        .addOnFailureListener(e -> {

                            Toast.makeText(requireContext(), "DataBase error", Toast.LENGTH_SHORT).show();


                        });







                // Start listening for new proposals










            }
        });









        return view;
    }



    private void sendNotificationToAllUsers(String proposalName, String proposalPrice, List<String> userTokens) {


        for (String userToken : userTokens) {
            sendNotifications(userToken, proposalName, proposalPrice);
        }
    }


   public void sendNotifications(String userToken,String proposalName,String proposalType){
       Data data=new Data(proposalName,proposalType);



       NotificationSender sender=new NotificationSender(data,userToken);




       apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
           @Override
           public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

               if(response.code()==200){

                   if(response.body().success !=1){

                       Toast.makeText(requireContext(),"FAILED",Toast.LENGTH_LONG).show();


                   }

               }

           }

           @Override
           public void onFailure(Call<MyResponse> call, Throwable t) {

           }
       });


   }



    private void getUsersTokensFromFirestore(String proposalName, String proposalPrice) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> userTokens = new ArrayList<>();

        db.collection("investorPreference")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userToken = document.getString("fcmToken");
                            if (userToken != null) {
                                userTokens.add(userToken);
                            }
                        }
                        sendNotificationToAllUsers(proposalName, proposalPrice, userTokens);
                    } else {
                        // Handle the error if the query fails
                    }
                });
    }


}