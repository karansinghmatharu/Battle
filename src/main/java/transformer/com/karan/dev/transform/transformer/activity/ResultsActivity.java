package transformer.com.karan.dev.transform.transformer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import transformer.com.karan.dev.transform.transformer.model.Transformer;

/**
 * Created by KaranDeepSingh
 *
 * @author KaranDeepSingh
 */

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView winnerImage;
    private TextView tv_winningTeam, tv_battles, tv_survivors, tv_game_over;
    private boolean gameOver = false;
    private int noOfBattles;
    private Button playAgain;
    private String winner;
    private RelativeLayout resultLayout;
    private ArrayList<Transformer> winnersList = new ArrayList<>();
    private ArrayList<Transformer> survivors = new ArrayList<>();
    private String survivorsString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle resultSet = getIntent().getExtras();
        winnersList = resultSet.getParcelableArrayList("winnersList");
        survivors = resultSet.getParcelableArrayList("survivors");
        gameOver = resultSet.getBoolean("gameOver");
        noOfBattles = resultSet.getInt("noOfBattles");
        winner = resultSet.getString("winner");

        tv_winningTeam = (TextView) findViewById(R.id.tv_win_team_name);
        tv_battles = (TextView) findViewById(R.id.tv_battles);
        tv_survivors = (TextView) findViewById(R.id.tv_survivors);
        tv_game_over = (TextView) findViewById(R.id.tv_game_over);
        resultLayout = (RelativeLayout) findViewById(R.id.resultsRL);
        winnerImage = (ImageView) findViewById(R.id.winnerImage);
        playAgain = (Button) findViewById(R.id.playAgain);
        playAgain.setOnClickListener(this);
        if (gameOver) {
            tv_game_over.setVisibility(View.VISIBLE);
            resultLayout.setVisibility(View.GONE);
        } else {
            tv_game_over.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
            setResults();
        }
    }

    private void setResults() {


        if (survivors.size() != 0) {
            for (Transformer t : survivors) {
                if (!t.getType().equalsIgnoreCase(winner))
                    survivorsString += t.getName() + " ";
                else {
                    survivorsString = " none.";
                }
            }
            tv_survivors.setText(getResources().getString(R.string.survivors) + " " + survivorsString);
        } else {
            tv_survivors.setText(getResources().getString(R.string.survivors) + " none.");
        }
        if (winner.equalsIgnoreCase("Tie")) {
            tv_survivors.setText(getResources().getString(R.string.tie_survivor) + survivorsString);
            tv_battles.setText(getResources().getString(R.string.battles) + " " + noOfBattles);
            tv_winningTeam.setText(getResources().getString(R.string.tie_text));
            winnerImage.setImageResource(R.drawable.draw);
            winnerImage.setBackgroundColor(getResources().getColor(R.color.orange_light));
            return;
        }

        tv_winningTeam.setText(getResources().getString(R.string.winning_team) + " " + winner);
        tv_battles.setText(getResources().getString(R.string.battles) + " " + noOfBattles);
        if (winner.equalsIgnoreCase("Autobot")) {
            winnerImage.setImageResource(R.drawable.autobot);
            winnerImage.setBackgroundColor(getResources().getColor(R.color.blue_dark));
        } else {
            winnerImage.setImageResource(R.drawable.decpticon);
            winnerImage.setBackgroundColor(getResources().getColor(R.color.red_dark));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.playAgain) {
            Intent home = new Intent(ResultsActivity.this, LauncherActivity.class);
            startActivity(home);
            finish();
        }

    }
}
