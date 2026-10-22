package com.littleorbit.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(12, 13, 30), panel = Color.rgb(27, 29, 56), accent = Color.rgb(164, 128, 255), text = Color.rgb(246, 244, 255), muted = Color.rgb(180, 178, 205);
    private LinearLayout root, body, nav;
    private String active = "Home";

    @Override public void onCreate(Bundle state) { super.onCreate(state); getWindow().setStatusBarColor(bg); getWindow().setNavigationBarColor(bg); show("Home"); }

    private GradientDrawable shape(int color, int radius) { GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(radius); return d; }
    private TextView label(String value, int size, int color, boolean bold) { TextView v = new TextView(this); v.setText(value); v.setTextSize(size); v.setTextColor(color); if (bold) v.setTypeface(Typeface.DEFAULT, Typeface.BOLD); v.setPadding(0, 5, 0, 5); return v; }
    private LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); return l; }
    private void pad(View v, int left, int top, int right, int bottom) { v.setPadding(left,top,right,bottom); }
    private void show(String page) {
        active = page;
        root = column(); root.setBackgroundColor(bg); pad(root,18,12,18,12);
        TextView brand = label("◉  LITTLE ORBIT", 19, text, true); root.addView(brand);
        root.addView(label("A little closer, even from far away.", 13, muted, false));
        ScrollView scroll = new ScrollView(this); body = column(); pad(body,0,16,0,12); scroll.addView(body); root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        nav = new LinearLayout(this); nav.setGravity(Gravity.CENTER); nav.setOrientation(LinearLayout.HORIZONTAL); nav.setBackground(shape(panel,22)); pad(nav,3,4,3,4);
        String[] tabs={"Home","Chat","Daily","Games","Memories"}; for(String tab:tabs){ Button b=button(tab.equals(page)?"● "+tab:tab); b.setTextSize(10); b.setPadding(5,12,5,12); b.setBackgroundColor(Color.TRANSPARENT); b.setTextColor(tab.equals(page)?accent:muted); nav.addView(b,new LinearLayout.LayoutParams(0,-2,1)); b.setOnClickListener(v->show(tab)); }
        root.addView(nav); setContentView(root);
        switch(page){ case "Home": home(); break; case "Chat": chat(); break; case "Daily": daily(); break; case "Games": games(); break; case "Memories": memories(); break; case "Calls": calls(); break; }
    }
    private Button button(String title) { Button b=new Button(this); b.setText(title); b.setAllCaps(false); b.setTextColor(text); b.setBackground(shape(accent,18)); b.setPadding(14,10,14,10); return b; }
    private void card(String title,String desc) { LinearLayout c=column(); c.setBackground(shape(panel,22)); pad(c,16,14,16,14); c.addView(label(title,17,text,true)); c.addView(label(desc,13,muted,false)); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.bottomMargin=12; body.addView(c,lp); }
    private void home(){
        body.addView(label("Your shared little universe ✦",26,text,true)); body.addView(label("Small moments make a long distance feel shorter.",14,muted,false));
        LinearLayout hero=column(); hero.setGravity(Gravity.CENTER); hero.setBackground(shape(Color.rgb(53,39,91),26)); pad(hero,16,26,16,26); hero.addView(label("☾   ✦   ☼",30,Color.WHITE,true)); hero.addView(label("Your orbit starts here",19,Color.WHITE,true)); hero.addView(label("Invite your person to make this space yours.",13,Color.rgb(226,216,255),false)); LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,-2); hp.topMargin=18; hp.bottomMargin=18; body.addView(hero,hp);
        card("💬  Messages","A cozy place for your conversations."); card("☀  Daily check-in","Share how your day is going."); card("🎮  Together time","Pick a tiny game and play along."); card("📸  Memories","Keep your favourite moments close.");
        Button call=button("♡  Open call screen"); body.addView(call); call.setOnClickListener(v->show("Calls"));
        body.addView(label("Preview build · Shared online features need a connected backend.",11,muted,false));
    }
    private void chat(){ body.addView(label("Messages",25,text,true)); body.addView(label("This starter build stores messages only on this device.",13,muted,false)); final LinearLayout messages=column(); messages.setBackground(shape(panel,20)); pad(messages,14,14,14,14); messages.addView(label("Little Orbit",12,accent,true)); messages.addView(label("Your chat space is ready. Say something kind ✨",15,text,false)); body.addView(messages,new LinearLayout.LayoutParams(-1,-2)); EditText input=new EditText(this); input.setSingleLine(true); input.setHint("Write a message…"); input.setHintTextColor(muted); input.setTextColor(text); input.setBackground(shape(panel,16)); pad(input,12,10,12,10); LinearLayout.LayoutParams ip=new LinearLayout.LayoutParams(-1,-2); ip.topMargin=12; body.addView(input,ip); Button send=button("Send message"); body.addView(send); send.setOnClickListener(v->{String s=input.getText().toString().trim(); if(s.isEmpty())return; messages.addView(label(s,15,text,false)); input.setText(""); Toast.makeText(this,"Saved in this session on this device",Toast.LENGTH_SHORT).show();}); }
    private void daily(){ body.addView(label("Daily check-in",25,text,true)); card("How are you feeling today?","Choose a mood and leave a little note for your person."); String[] moods={"😊 Happy","🥰 Loved","😴 Tired","😔 Low","⚡ Excited"}; for(String m:moods){Button b=button(m); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=8;body.addView(b,p);b.setOnClickListener(v->Toast.makeText(this,"Mood selected: "+m,Toast.LENGTH_SHORT).show());} }
    private void games(){ body.addView(label("Together games",25,text,true)); card("Quick questions","Take turns asking fun questions."); card("Would you rather?","Two choices, one very important debate."); card("Memory challenge","See what little details you remember."); body.addView(label("Game screens are a starter UI; multiplayer sync is not connected yet.",12,muted,false)); }
    private void memories(){ body.addView(label("Our memories",25,text,true)); card("Your memory box","A place for photos, dates, and little moments."); Button add=button("＋  Add a memory note"); body.addView(add); add.setOnClickListener(v->{EditText e=new EditText(this);e.setHint("Write a memory…");e.setTextColor(text);e.setHintTextColor(muted);body.addView(e);Button save=button("Save note on this device");body.addView(save);save.setOnClickListener(w->{if(!e.getText().toString().trim().isEmpty()){body.addView(label("✦  "+e.getText().toString(),15,text,false));e.setEnabled(false);save.setEnabled(false);}});}); }
    private void calls(){ body.addView(label("Calls",25,text,true)); card("Make time for each other","Voice and video calls need a real-time calling service and account setup."); Button b=button("Call feature status"); body.addView(b); b.setOnClickListener(v->Toast.makeText(this,"Calling is not connected in this starter build yet.",Toast.LENGTH_LONG).show()); }
}
