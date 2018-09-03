package transformer.com.karan.dev.transform.transformer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import transformer.com.karan.dev.transform.transformer.adapter.TransformerAdapter;
import transformer.com.karan.dev.transform.transformer.dialogfragment.TransformerDialogFragment;
import transformer.com.karan.dev.transform.transformer.model.Transformer;


/**
 * Created by KaranDeepSingh
 *
 * @author KaranDeepSingh
 */
public class LauncherActivity extends AppCompatActivity implements View.OnClickListener, TransformerDialogFragment.TransformerListener {

    private Button addAutoBots, addDecps, fightButton;
    private ArrayList<Transformer> autoBotsList = new ArrayList<>();
    private ArrayList<Transformer> decpList = new ArrayList<>();
    private RecyclerView recyclerViewAuto, recyclerViewDecp;
    private TransformerAdapter tAdapter, dAdapter;
    private TextView generalAuto, generalDecp;
    private boolean gameOver = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        addAutoBots = (Button) findViewById(R.id.addAutobot);
        addDecps = (Button) findViewById(R.id.addDecp);
        fightButton = (Button) findViewById(R.id.fightButton);
        addAutoBots.setOnClickListener(this);
        addDecps.setOnClickListener(this);
        fightButton.setOnClickListener(this);
        recyclerViewAuto = (RecyclerView) findViewById(R.id.rABotView);
        recyclerViewDecp = (RecyclerView) findViewById(R.id.rDecpView);

        generalAuto = (TextView) findViewById(R.id.generalAuto);
        generalDecp = (TextView) findViewById(R.id.generalDecp);
        if (autoBotsList.isEmpty()) {
            generalAuto.setVisibility(View.VISIBLE);
            recyclerViewAuto.setVisibility(View.VISIBLE);
        } else {
            generalAuto.setVisibility(View.GONE);
            recyclerViewAuto.setVisibility(View.VISIBLE);
        }

        if (decpList.isEmpty()) {
            generalDecp.setVisibility(View.VISIBLE);
            recyclerViewDecp.setVisibility(View.VISIBLE);
        } else {
            generalDecp.setVisibility(View.GONE);
            recyclerViewDecp.setVisibility(View.VISIBLE);
        }
        recyclerViewAuto.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAuto.setHasFixedSize(true);
        recyclerViewDecp.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDecp.setHasFixedSize(true);


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addAutobot) {
            openDialog("AutoBot");

        }
        if (v.getId() == R.id.addDecp) {
            openDialog("Decepticon");
        }
        if (v.getId() == R.id.fightButton) {
            battle(autoBotsList, decpList);
        }

    }

    private void battle(ArrayList<Transformer> autoBotsList, ArrayList<Transformer> decpList) {

        int sizeAuto = autoBotsList.size();
        int sizeDecep = decpList.size();
        ArrayList<Transformer> survivors = new ArrayList<>();
        int noOfBattles = 1;
        if (sizeAuto == sizeDecep)
            noOfBattles = sizeAuto;
        else if (sizeAuto < sizeDecep) {
            noOfBattles = sizeAuto;
            survivors = computeSurvivors(decpList, noOfBattles);
        } else if (sizeDecep < sizeAuto) {
            noOfBattles = sizeDecep;
            survivors = computeSurvivors(autoBotsList, noOfBattles);
        }
        compute(autoBotsList, decpList, noOfBattles, survivors);


    }

    private ArrayList<Transformer> computeSurvivors(ArrayList<Transformer> transformersList, int noOfBattles) {
        ArrayList<Transformer> survivors = new ArrayList<>();

        for (Transformer e : transformersList) {
            if (noOfBattles <= 0) {
                survivors.add(e);
            }
            noOfBattles--;
        }
        return survivors;
    }

    private void compute(ArrayList<Transformer> autoBotsList, ArrayList<Transformer> decpList, int noOfBattles, ArrayList<Transformer> survivors) {
        Transformer autobot = null, decep = null;
        int autoCount = 0, decpCount = 0;
        String winner = null;

        ArrayList<Transformer> winnersAutoBots = new ArrayList<>();
        ArrayList<Transformer> winnersDecp = new ArrayList<>();
        ArrayList<Transformer> diedAutoBots = new ArrayList<>();
        ArrayList<Transformer> diedDecp = new ArrayList<>();
        for (int i = 0; i < noOfBattles; i++) {
            autobot = autoBotsList.get(i);
            decep = decpList.get(i);

            //special rules part2 pk vs pk or op vs pk or op vs op
            if ((autobot.getName().equalsIgnoreCase("Optimus Prime") && decep.getName().equalsIgnoreCase("Optimus Prime")) ||
                    autobot.getName().equalsIgnoreCase("Optimus Prime") && decep.getName().equalsIgnoreCase("Predaking") ||
                    decep.getName().equalsIgnoreCase("Predaking") && autobot.getName().equalsIgnoreCase("Predaking") ||
                    decep.getName().equalsIgnoreCase("Predaking") && autobot.getName().equalsIgnoreCase("Predaking")) {
                gameOver = true;
                callResult(gameOver, noOfBattles, winner, winnersDecp, survivors, diedAutoBots);
                break;

            }
            //special rules part1 op vs
            if ((autobot.getName().equalsIgnoreCase("Optimus Prime")) &&
                    ((!decep.getName().equalsIgnoreCase("Optimus Prime")) || !decep.getName().equalsIgnoreCase("Predaking"))) {
                winnersAutoBots.add(autobot);
                diedDecp.add(decep);
                continue;
            }
            //special rules part1 pk vs
            if ((decep.getName().equalsIgnoreCase("Predaking")) &&
                    ((!autobot.getName().equalsIgnoreCase("Optimus Prime")) || !autobot.getName().equalsIgnoreCase("Predaking"))) {
                winnersDecp.add(decep);
                diedAutoBots.add(autobot);
                continue;

            }

            //rule 1,2,3
            if (((decep.getCourage() - autobot.getCourage()) >= 4 && (decep.getStrength() - autobot.getStrength() >= 3))) {
                winnersDecp.add(decep);
                diedAutoBots.add(autobot);
                decpCount++;
                continue;
            } else if (((autobot.getCourage() - decep.getCourage()) >= 4 && (autobot.getStrength() - decep.getStrength() >= 3))) {
                winnersAutoBots.add(autobot);
                diedDecp.add(decep);
                autoCount++;
                continue;
            }
            //rule 2
            if ((decep.getSkill() - autobot.getSkill()) >= 3) {
                winnersDecp.add(decep);
                diedAutoBots.add(autobot);
                decpCount++;
                continue;
            } else if ((autobot.getSkill() - decep.getSkill()) >= 3) {
                winnersAutoBots.add(autobot);
                diedDecp.add(decep);
                autoCount++;
                continue;
            }
            //rule 3
            if (decep.getOverall() > autobot.getOverall()) {
                winnersDecp.add(decep);
                diedAutoBots.add(autobot);
                decpCount++;
                continue;
            } else if ((autobot.getOverall() > decep.getOverall())) {
                winnersAutoBots.add(autobot);
                diedDecp.add(decep);
                autoCount++;
                continue;
            }

            //tie both die
            if (autobot.getOverall() == decep.getOverall()) {
                diedDecp.add(decep);
                diedAutoBots.add(autobot);
                if (autoBotsList.size() == decpList.size() && decpList.size() == 1)//size is 1
                {
                    callResult(gameOver, noOfBattles, "Tie", winnersDecp, survivors, diedAutoBots);
                    return;
                }
                if (autobot.getCourage() - decep.getCourage() == 0 && autobot.getStrength() - decep.getStrength() == 0) {
                    if (autobot.getSkill() - decep.getSkill() == 0)
                        callResult(gameOver, noOfBattles, "Tie", winnersDecp, survivors, diedAutoBots);
                    return;
                }
                continue;


            }


        }
        winner = (decpCount > autoCount) ? "Decpticon" : "Autobot";
        if (winner.equalsIgnoreCase("Decpticon"))
            callResult(gameOver, noOfBattles, winner, winnersDecp, survivors, diedAutoBots);
        else callResult(gameOver, noOfBattles, winner, winnersAutoBots, survivors, diedDecp);

    }

    private void callResult(boolean gameOver, int noOfBattles, String winner, ArrayList<Transformer> winnersList, ArrayList<Transformer> survivors, ArrayList<Transformer> diedTransformers) {
        Intent result = new Intent(LauncherActivity.this, ResultsActivity.class);
        //send results
        Bundle resultBundle = new Bundle();
        resultBundle.putParcelableArrayList("winnersList", winnersList);
        resultBundle.putParcelableArrayList("survivors", survivors);
        resultBundle.putParcelableArrayList("diedTransformers", diedTransformers);
        resultBundle.putBoolean("gameOver", gameOver);
        resultBundle.putInt("noOfBattles", noOfBattles);
        resultBundle.putString("winner", winner);
        result.putExtras(resultBundle);
        startActivity(result);
        finish();
    }

    private void openDialog(String type) {
        TransformerDialogFragment dialogFragment = new TransformerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("autobotslist", autoBotsList);
        bundle.putParcelableArrayList("decpList", decpList);
        bundle.putString("type", type);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "Enter " + type);
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
    public void addAutobotList(ArrayList<Transformer> autoBotsList) {
        this.autoBotsList = autoBotsList;
        Collections.sort(autoBotsList, new Comparator<Transformer>() {
            @Override
            public int compare(Transformer t1, Transformer t2) {
                return t1.getRank() - t2.getRank();
            }
        });
        if (!autoBotsList.isEmpty()) {
            generalAuto.setVisibility(View.GONE);
            recyclerViewAuto.setVisibility(View.VISIBLE);
        }
        if (!autoBotsList.isEmpty() && !decpList.isEmpty()) {
            fightButton.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            fightButton.startAnimation(anim);
//            Toast.makeText(getApplicationContext(), "Click on Fight", Toast.LENGTH_SHORT).show();
        } else {
            fightButton.setVisibility(View.INVISIBLE);
        }
        if (tAdapter == null)
            tAdapter = new TransformerAdapter(autoBotsList);
        recyclerViewAuto.setAdapter(tAdapter);
        tAdapter.notifyDataSetChanged();

    }

    @Override
    public void addDecpList(ArrayList<Transformer> decpList) {

        this.decpList = decpList;
        Collections.sort(decpList, new Comparator<Transformer>() {
            @Override
            public int compare(Transformer t1, Transformer t2) {
                return t1.getRank() - t2.getRank();
            }
        });
        if (!decpList.isEmpty()) {
            generalDecp.setVisibility(View.GONE);
            recyclerViewDecp.setVisibility(View.VISIBLE);
        }
        if (!autoBotsList.isEmpty() && !decpList.isEmpty()) {
            fightButton.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            fightButton.startAnimation(anim);
//            Toast.makeText(getApplicationContext(), "Click on Fight", Toast.LENGTH_SHORT).show();
        } else {
            fightButton.setVisibility(View.INVISIBLE);
        }
        if (dAdapter == null)
            dAdapter = new TransformerAdapter(decpList);
        recyclerViewDecp.setAdapter(dAdapter);
        dAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
    }
}
