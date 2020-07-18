package com.example.fruitturn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class WheelActivity extends AppCompatActivity {
    ImageView wheel, playBtn;
    TextView score;
    public static final Random random = new Random();
    private static final float FACTOR = 10f;
    int degree = 0, degree_old = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);

        playBtn = findViewById(R.id.play_btn);
        score = findViewById(R.id.money_count);
        wheel = findViewById(R.id.wheel);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinWheel();
            }
        });
    }

    private void spinWheel() {
        degree_old = degree % 360;
        degree = random.nextInt(3600) + 720;
        RotateAnimation rotateAnimation = new RotateAnimation(degree_old, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                count += currentScore(360 - (degree%360));
                score.setText(count + "");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotateAnimation);


    }

    private Integer currentScore (int degrees) {
        int score = 0;

        if (degrees >= (FACTOR) && degrees < (FACTOR * 3)) {
            score=1300;
        }
        if (degrees >= (FACTOR * 3) && degrees < (FACTOR * 5)) {
            score=10;
        }
        if (degrees >= (FACTOR * 5) && degrees < (FACTOR * 7)) {
            score=20;
        }
        if (degrees >= (FACTOR * 7) && degrees < (FACTOR * 9)) {
            score=50;
        }
        if (degrees >= (FACTOR * 9) && degrees < (FACTOR * 11)) {
            score=100;
        }
        if (degrees >= (FACTOR* 11) && degrees < (FACTOR * 13)) {
            score=150;
        }
        if (degrees >= (FACTOR * 13) && degrees < (FACTOR * 15)) {
            score=200;
        }
        if (degrees >= (FACTOR * 15) && degrees < (FACTOR * 17)) {
            score=250;
        }
        if (degrees >= (FACTOR * 17) && degrees < (FACTOR * 19)) {
            score=300;
        }
        if (degrees >= (FACTOR * 19) && degrees < (FACTOR * 21)) {
            score=400;
        }
        if (degrees >= (FACTOR * 21) && degrees < (FACTOR * 23)) {
            score=500;
        }
        if (degrees >= (FACTOR * 23) && degrees < (FACTOR * 25)) {
            score=600;
        }
        if (degrees >= (FACTOR * 25) && degrees < (FACTOR * 27)) {
            score=700;
        }
        if (degrees >= (FACTOR * 27) && degrees < (FACTOR * 29)) {
            score=800;
        }
        if (degrees >= (FACTOR * 29) && degrees < (FACTOR * 31)) {
            score=2000;
        }
        if (degrees >= (FACTOR * 31) && degrees < (FACTOR * 33)) {
            score=1000;
        }
        if (degrees >= (FACTOR * 33) && degrees < (FACTOR * 35)) {
            score=1100;
        }
        if ((degrees >= (FACTOR * 135) && degrees < 360) || (degrees >= 0 && degrees < (FACTOR *1))) {
            score=1200;
        }


        return score;
    }
}
