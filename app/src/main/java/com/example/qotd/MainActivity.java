package com.example.qotd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    public TextView quoteview;
    public ImageView nextButton,previousButton,shareButton,LikeButton;
    public ArrayList<Quote> quotelist;
    public Stack<Quote> previousQuote;
    public int index;
    public boolean isPrevious;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteview=(TextView) findViewById(R.id.quotetv);
        nextButton=(ImageView) findViewById(R.id.nextbutton);
        previousButton=(ImageView) findViewById(R.id.previousbutton);
        shareButton=(ImageView) findViewById(R.id.sharebutton);
        LikeButton=(ImageView) findViewById(R.id.likebutton);

        Resources res=getResources();
        String[] allquotes=res.getStringArray(R.array.quotes);
        quotelist=new ArrayList<>();
        addToQuoteList(allquotes);

        previousQuote=new Stack<>();

        final int quotelength=quotelist.size();
        index=getRandomQuote(quotelength-1);
        quoteview.setText(quotelist.get(index).toString());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPrevious=false;
                index=getRandomQuote(quotelength-1);
                quoteview.setText(quotelist.get(index).toString());
                previousQuote.push(quotelist.get(index));

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPrevious&&previousQuote.size()>0) {
                    previousQuote.pop();
                    isPrevious=true;
                }
                if(previousQuote.size()>0&& isPrevious)
                     quoteview.setText(previousQuote.pop().toString());
                else
                    Toast.makeText(MainActivity.this,"Get new Quotes!!",Toast.LENGTH_SHORT).show();

            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sentIntent=new Intent();
                sentIntent.setAction(Intent.ACTION_SEND);
                sentIntent.putExtra(Intent.EXTRA_TEXT,quotelist.get(index).toString());
                sentIntent.setType("text/plain");
                startActivity(sentIntent);

            }
        });

        LikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quotelist.get(index).isFavorite()){
                    LikeButton.setImageResource(R.mipmap.outlike);
                    quotelist.get(index).setFavorite(false);
                }
                else{
                    LikeButton.setImageResource(R.mipmap.like);
                    quotelist.get(index).setFavorite(true);
                }
            }
        });

    }
    public void addToQuoteList(String[] allQuotes){
        for(int i=0;i<allQuotes.length;i++){
            String quote=allQuotes[i];
            Quote newquote=new Quote(quote);
            quotelist.add(newquote);
        }
    }

   public int getRandomQuote(int length){
        return (int) (Math.random()*length)+1;
   }









}