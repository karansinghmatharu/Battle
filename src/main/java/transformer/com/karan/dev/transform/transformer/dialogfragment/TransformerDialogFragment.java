package transformer.com.karan.dev.transform.transformer.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import transformer.com.karan.dev.transform.transformer.activity.R;
import transformer.com.karan.dev.transform.transformer.model.Transformer;

/**
 * Created by KaranDeepSingh
 *
 * @author KaranDeepSingh
 */


public class TransformerDialogFragment extends AppCompatDialogFragment {

    private EditText transformerName;
    private SeekBar sb_strength, sb_intell, sb_speed, sb_endu, sb_rank, sb_cour, sb_fp, sb_skill;
    private TextView tv_enter_transformer;
    private int overAllRating;
    private ArrayList<Transformer> autobotsList = new ArrayList<>();
    private ArrayList<Transformer> decpList = new ArrayList<>();
    private String name, type;
    private int strength, intell, speed, endu, rank, cour, fp, skill;
    private TransformerListener listener;
    private ImageView imageTransform;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TransformerListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement TransformerListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        autobotsList = getArguments().getParcelableArrayList("autobotslist");
        decpList = getArguments().getParcelableArrayList("decpList");

        type = getArguments().getString("type");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.lauout_transformer_autobot_dialog, null);
        transformerName = (EditText) view.findViewById(R.id.ed_name);
        sb_strength = (SeekBar) view.findViewById(R.id.sb_strength);
        sb_intell = (SeekBar) view.findViewById(R.id.sb_intell);
        sb_speed = (SeekBar) view.findViewById(R.id.sb_speed);
        sb_endu = (SeekBar) view.findViewById(R.id.sb_endu);
        sb_rank = (SeekBar) view.findViewById(R.id.sb_rank);
        sb_cour = (SeekBar) view.findViewById(R.id.sb_cour);
        sb_fp = (SeekBar) view.findViewById(R.id.sb_fp);
        sb_skill = (SeekBar) view.findViewById(R.id.sb_skill);
        imageTransform = (ImageView) view.findViewById(R.id.imageTransform);
        tv_enter_transformer = (TextView) view.findViewById(R.id.tv_enter_transformer);
        if (type.equalsIgnoreCase("autobot")) {

            tv_enter_transformer.setText(getActivity().getString(R.string.enter_trans) + " " + type);

            imageTransform.setImageResource(R.drawable.autobot);
            builder.setView(view).setTitle("Enter the " + type).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setPositiveButton("Add " + type, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (transformerName.getText().toString().matches("")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Autobot not added as no name specified", Toast.LENGTH_SHORT).show();

                    } else {
                        name = transformerName.getText().toString();
                        strength = sb_strength.getProgress() + 1;
                        intell = sb_intell.getProgress() + 1;
                        speed = sb_speed.getProgress() + 1;
                        endu = sb_endu.getProgress() + 1;
                        rank = sb_rank.getProgress() + 1;
                        cour = sb_cour.getProgress() + 1;
                        fp = sb_fp.getProgress() + 1;
                        skill = sb_skill.getProgress() + 1;
                        overAllRating = computeOverAllRating(strength, intell, speed, endu, fp);
                        Transformer auto = new Transformer(name, type, strength, intell, speed, endu, rank, cour, fp, skill, overAllRating);
                        autobotsList.add(auto);
                        listener.addAutobotList(autobotsList);

                    }

                }
            });
        } else {
            tv_enter_transformer.setText(getActivity().getString(R.string.enter_trans) + " " + type);
            imageTransform.setImageResource(R.drawable.decpticon);
            builder.setView(view).setTitle("Enter the " + type).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setPositiveButton("Add " + type, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (transformerName.getText().toString().matches("")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Decepticon not added as no name specified", Toast.LENGTH_SHORT).show();

                    } else {
                        name = transformerName.getText().toString();
                        strength = sb_strength.getProgress() + 1;
                        intell = sb_intell.getProgress() + 1;
                        speed = sb_speed.getProgress() + 1;
                        endu = sb_endu.getProgress() + 1;
                        rank = sb_rank.getProgress() + 1;
                        cour = sb_cour.getProgress() + 1;
                        fp = sb_fp.getProgress() + 1;
                        skill = sb_skill.getProgress() + 1;
                        overAllRating = computeOverAllRating(strength, intell, speed, endu, fp);
                        Transformer decp = new Transformer(name, type, strength, intell, speed, endu, rank, cour, fp, skill, overAllRating);
                        decpList.add(decp);
                        listener.addDecpList(decpList);

                    }

                }
            });
        }
        return builder.create();
    }

    private int computeOverAllRating(int strength, int intell, int speed, int endu, int fp) {
        return strength + intell + speed + endu + fp;
    }

    public interface TransformerListener {
        void addAutobotList(ArrayList<Transformer> autoBotsList);

        void addDecpList(ArrayList<Transformer> decpList);
    }
}
