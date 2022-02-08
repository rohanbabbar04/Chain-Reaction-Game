package com.example.chainreactiongameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HowToPlay extends AppCompatActivity {

    int ruleNoCount=1;
    TextView ruleNo, ruleDescription;
    Button nextButton,prevButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        ruleNo=findViewById(R.id.ruleNo);
        ruleDescription=findViewById(R.id.ruleDescription);
        nextButton=findViewById(R.id.nextButton);
        prevButton=findViewById(R.id.previousButton);
        imageView=findViewById(R.id.imageView);

        ruleDescription.setText(getResources().getString(R.string.ruleOne));
        ruleNo.setText("Rule No. 1");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(ruleNoCount<=6)
               {
                   ruleNoCount++;
                   String str="Rule No. "+ ruleNoCount;
                   ruleNo.setText(str);

                   if(ruleNoCount==2)
                   {
                       ruleDescription.setText(getResources().getString(R.string.ruleTwo));
                       imageView.setImageResource(R.drawable.ic_board_2);
                   }
                   else if(ruleNoCount==3)
                   {
                       ruleDescription.setText(getResources().getString(R.string.ruleThree));
                   }
                   else if(ruleNoCount==4)
                   {
                       ruleDescription.setText(getResources().getString(R.string.ruleFour));
                   }
                   else if(ruleNoCount==5)
                   {
                       ruleDescription.setText(getResources().getString(R.string.ruleFive));
                   }
                   else if(ruleNoCount==6)
                   {
                       ruleDescription.setText(getResources().getString(R.string.ruleSix));
                       nextButton.setText("Play Now");
                       ruleNoCount++;
                   }
               }
               else if (ruleNoCount==7)
               {
                   startActivity(new Intent(HowToPlay.this, MainActivity.class));
               }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ruleNoCount>1)
                {
                    ruleNoCount--;
                    String str="Rule No. "+ ruleNoCount;
                    ruleNo.setText(str);

                    if(ruleNoCount==1)
                    {
                        ruleDescription.setText(getResources().getString(R.string.ruleOne));
                        imageView.setImageResource(R.drawable.ic_board);
                    }
                    else if(ruleNoCount==2)
                    {
                        ruleDescription.setText(getResources().getString(R.string.ruleTwo));
                        imageView.setImageResource(R.drawable.ic_board_2);
                    }
                    else if(ruleNoCount==3)
                    {
                        ruleDescription.setText(getResources().getString(R.string.ruleThree));
                    }
                    else if(ruleNoCount==4)
                    {
                        ruleDescription.setText(getResources().getString(R.string.ruleFour));
                    }
                    else if(ruleNoCount==5)
                    {
                        ruleDescription.setText(getResources().getString(R.string.ruleFive));
                    }
                }
                else
                {
                    startActivity(new Intent(HowToPlay.this,MainActivity.class));
                }

            }
        });
    }
}
