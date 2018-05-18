package pt.ulisboa.tecnico.cmu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import pt.ulisboa.tecnico.cmu.DataObjects.Tour;
import pt.ulisboa.tecnico.cmu.client.ResponseHandlerImpl;
import pt.ulisboa.tecnico.cmu.command.UpdateRankingCommand;
import pt.ulisboa.tecnico.cmu.response.UpdateRankingResponse;

public class RankingActivity extends AppCompatActivity {

  public static boolean updated = false;

  private TextView text_tour_title_ranking;
  private ListView list_ranking;
  private ListAdapter scoreAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ranking);

    this.text_tour_title_ranking = findViewById(R.id.text_tour_title_ranking);
    this.text_tour_title_ranking.setText(MainActivity.tour.getName());

    this.list_ranking = findViewById(R.id.list_ranking);
    this.scoreAdapter = new CustomRankingListAdapter(this, (ArrayList) MainActivity.tour.getScores());
    this.list_ranking.setAdapter(this.scoreAdapter);
  }

  @Override
  protected void onStart() {
    super.onStart();
    new UpdateRankingAction().execute();
  }

  private void updateScoreList() {
    this.scoreAdapter = new CustomRankingListAdapter(this, (ArrayList) MainActivity.tour.getScores());
    this.list_ranking.setAdapter(this.scoreAdapter);
  }

  public class UpdateRankingAction extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {

      Socket server = null;
      ResponseHandlerImpl rhi = new ResponseHandlerImpl();
      UpdateRankingCommand urc = new UpdateRankingCommand(MainActivity.sessionId);

      try {
        server = new Socket(MainActivity.HOST, MainActivity.PORT);

        ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
        oos.writeObject(urc);

        ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
        UpdateRankingResponse urr = (UpdateRankingResponse) ois.readObject();
        urr.handle(rhi);

        oos.close();
        ois.close();
        Log.d(Constants.LOG_TAG, "UpdateRankingAction");
      } catch (Exception e) {
        Log.d(Constants.LOG_TAG, "UpdateRankingAction failed..." + e.getMessage());
        e.printStackTrace();
      } finally {
        if (server != null) {
          try {
            server.close();
          } catch (Exception e) {
          }
        }
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      if (RankingActivity.updated) {
        RankingActivity.updated = false;
        RankingActivity.this.updateScoreList();
      } else {
        Toast.makeText(RankingActivity.this, Constants.TOAST_RANKING_UPDATE_FAILED,
            Toast.LENGTH_SHORT).show();
      }
    }
  }
}
