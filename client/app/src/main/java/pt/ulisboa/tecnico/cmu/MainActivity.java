package pt.ulisboa.tecnico.cmu;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;
import pt.ulisboa.tecnico.cmu.DataObjects.Location;
import pt.ulisboa.tecnico.cmu.DataObjects.Question;
import pt.ulisboa.tecnico.cmu.DataObjects.Quiz;
import pt.ulisboa.tecnico.cmu.DataObjects.Score;
import pt.ulisboa.tecnico.cmu.DataObjects.Tour;
import pt.ulisboa.tecnico.cmu.client.ResponseHandlerImpl;
import pt.ulisboa.tecnico.cmu.command.DownloadQuizzesCommand;
import pt.ulisboa.tecnico.cmu.command.GetTourDetailsCommand;
import pt.ulisboa.tecnico.cmu.command.LogoutCommand;
import pt.ulisboa.tecnico.cmu.response.DownloadQuizzesResponse;
import pt.ulisboa.tecnico.cmu.response.GetTourDetailsResponse;
import pt.ulisboa.tecnico.cmu.response.LogoutResponse;

public class MainActivity extends AppCompatActivity implements SimWifiP2pManager.PeerListListener {

  public static final String HOST = "10.0.2.2";
  public static final int PORT = 9090;
  public static String sessionId = null;
  public static Location currentLocation = new Location(null);
  public static Location nextLocation = new Location(null);
  public static Tour tour = null;
  public static boolean quizzesUpdated = false;
  public static List<Quiz> quizzesList = new ArrayList<Quiz>();

  private Button btnLogOut;
  private Button btnDownloadQuizzes;
  private Button btnRanking;
  private Button btnFinishDay;
  private Button btnLocations;
  private ListAdapter adapterQuiz;
  private ListView listQuizzes;

  private SimWifiP2pManager mManager = null;
  private SimWifiP2pManager.Channel mChannel = null;
  private Messenger mService = null;
  private boolean mBound = false;
  private SimWifiP2pSocketServer mSrvSocket = null;
  private SimWifiP2pSocket mCliSocket = null;
  private SimWifiP2pBroadcastReceiver mReceiver;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(pt.ulisboa.tecnico.cmu.R.layout.activity_main);

    this.btnLogOut = (Button) findViewById(R.id.button_logout);
    this.btnDownloadQuizzes = (Button) findViewById(R.id.button_download_quizzes);
    this.btnRanking = (Button) findViewById(R.id.button_ranking);
    this.btnFinishDay = (Button) findViewById(R.id.button_finish_day);
    this.btnLocations = (Button) findViewById(R.id.button_locations);
    this.listQuizzes = (ListView) findViewById(R.id.list_quizzes);
    this.updateQuizList();

    //Termite
    // initialize the WDSim API
    SimWifiP2pSocketManager.Init(getApplicationContext());

    // register broadcast receiver
    IntentFilter filter = new IntentFilter();
    filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
    filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
    filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
    filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
    mReceiver = new SimWifiP2pBroadcastReceiver(this);
    registerReceiver(mReceiver, filter);

    //A Toa
    Intent intent = new Intent(this, SimWifiP2pService.class);
    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    mBound = true;
    System.out.println("--- bound");

    this.btnLogOut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainActivity.this.logout();
        MainActivity.this.authenticate();
      }
    });

    this.btnDownloadQuizzes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (connectedToLocation()) {
          new GetTourDetailsAction().execute();
          new DownloadQuizzesAction().execute();
          MainActivity.this.updateQuizList();
        }
      }
    });

    this.btnRanking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(Constants.LOG_TAG, "onClick info: Ranking");
        Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
        intent.putExtra(Constants.EXTRA_TOUR, MainActivity.tour);
        startActivity(intent);
      }
    });

    this.btnFinishDay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(MainActivity.this, Constants.TOAST_DAY_FINISHED, Toast.LENGTH_SHORT).show();
      }
    });

    this.btnLocations.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new GetTourDetailsAction().execute();
        if (MainActivity.tour != null) {
          Intent intent = new Intent(getApplicationContext(), LocationsActivity.class);
          intent.putExtra(Constants.EXTRA_TOUR, MainActivity.tour);
          startActivity(intent);
        }
      }
    });

    this.listQuizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Quiz item = (Quiz) MainActivity.this.listQuizzes.getItemAtPosition(position);
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        intent.putExtra(Constants.EXTRA_QUIZ, item);
        startActivity(intent);
      }
    });

  }

  @Override
  protected void onStart() {
    super.onStart();

    if (MainActivity.sessionId == null) {
      this.authenticate();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == Constants.REQUEST_AUTH) {
      if (resultCode == Constants.AUTH_OK) {
        String sessionId = (String) data.getSerializableExtra(Constants.EXTRA_SESSION_ID);
        MainActivity.sessionId = sessionId;
        MainActivity.currentLocation = new Location(null);
        MainActivity.nextLocation = new Location(null);
        MainActivity.tour = null;
        MainActivity.quizzesUpdated = false;
        MainActivity.quizzesList = new ArrayList<Quiz>();
        if (connectedToLocation()) {
          new GetTourDetailsAction().execute();
          new DownloadQuizzesAction().execute();
          updateQuizList();
        }
      } else {  // resultCode == Constants.AUTH_FAILED
        this.finish();
      }
    }
  }

  private void authenticate() {
    Intent intent = new Intent(this, CredentialsActivity.class);
    startActivityForResult(intent, Constants.REQUEST_AUTH);
  }

  private void logout() {
    AsyncTask<Void, Void, Void> task = new LogoutAction();
    try {
      task.execute().get();
    } catch (InterruptedException e) {
      Log.d(Constants.LOG_TAG, "onClick error: InterruptedException");
    } catch (ExecutionException e) {
      Log.d(Constants.LOG_TAG, "onClick error: ExecutionException");
    }
  }

  public void updateQuizList() {
    this.adapterQuiz = new CustomQuizListAdapter(this, (ArrayList) MainActivity.quizzesList);
    this.listQuizzes.setAdapter(this.adapterQuiz);
  }

  private boolean connectedToLocation() {
    return true;
  }

  @Override
  public void onPeersAvailable(SimWifiP2pDeviceList peers) {
    StringBuilder peersStr = new StringBuilder();

    // compile list of devices in range
    for (SimWifiP2pDevice device : peers.getDeviceList()) {
      String devstr = "" + device.deviceName + " (" + device.getVirtIp() + ")\n";
      peersStr.append(devstr);
    }

    // display list of devices in range
    new AlertDialog.Builder(this)
            .setTitle("Devices in WiFi Range")
            .setMessage(peersStr.toString())
            .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
              }
            })
            .show();
  }

  public class DownloadQuizzesAction extends AsyncTask<Void, Void, Void> {

    private boolean mSuccessDownload = false;

    @Override
    protected Void doInBackground(Void... voids) {

      Socket server = null;
      ResponseHandlerImpl rhi = new ResponseHandlerImpl();
      DownloadQuizzesCommand dqc = new DownloadQuizzesCommand(MainActivity.sessionId,
          MainActivity.currentLocation.getName());

      try {
        server = new Socket(MainActivity.HOST, MainActivity.PORT);

        ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
        oos.writeObject(dqc);

        ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
        DownloadQuizzesResponse dqr = (DownloadQuizzesResponse) ois.readObject();
        dqr.handle(rhi);

        oos.close();
        ois.close();
        Log.d(Constants.LOG_TAG, "DownloadQuizzesAction");
      } catch (Exception e) {
        Log.d(Constants.LOG_TAG, "DownloadQuizzesAction failed..." + e.getMessage());
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
      if (MainActivity.quizzesUpdated) {
        MainActivity.quizzesUpdated = false;
        MainActivity.this.updateQuizList();
        Toast.makeText(MainActivity.this, Constants.TOAST_DOWNLOAD_QUIZZES_SUCCESS,
            Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(MainActivity.this, Constants.TOAST_DOWNLOAD_QUIZZES_FAILED,
            Toast.LENGTH_SHORT).show();
      }
    }
  }

  public class LogoutAction extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {

      Socket server = null;
      ResponseHandlerImpl rhi = new ResponseHandlerImpl();
      LogoutCommand lc = new LogoutCommand(MainActivity.sessionId);

      try {
        server = new Socket(MainActivity.HOST, MainActivity.PORT);

        ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
        oos.writeObject(lc);

        ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
        LogoutResponse lr = (LogoutResponse) ois.readObject();
        lr.handle(rhi);

        oos.close();
        ois.close();
        Log.d(Constants.LOG_TAG, "LogoutAction");
      } catch (Exception e) {
        Log.d(Constants.LOG_TAG, "LogoutAction failed..." + e.getMessage());
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
      Toast.makeText(MainActivity.this, Constants.TOAST_LOGOUT,
          Toast.LENGTH_SHORT).show();
    }
  }

  public class GetTourDetailsAction extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {

      Socket server = null;
      ResponseHandlerImpl rhi = new ResponseHandlerImpl();
      GetTourDetailsCommand gtdc = new GetTourDetailsCommand(MainActivity.sessionId,
          MainActivity.currentLocation.getName());

      try {
        server = new Socket(MainActivity.HOST, MainActivity.PORT);

        ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
        oos.writeObject(gtdc);

        ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
        GetTourDetailsResponse gtdr = (GetTourDetailsResponse) ois.readObject();
        gtdr.handle(rhi);

        oos.close();
        ois.close();
        Log.d(Constants.LOG_TAG, "GetTourDetailsAction");
      } catch (Exception e) {
        Log.d(Constants.LOG_TAG, "GetTourDetailsAction failed..." + e.getMessage());
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
      // Update Location activity
    }
  }

  private ServiceConnection mConnection = new ServiceConnection() {
    // callbacks for service binding, passed to bindService()

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
      mService = new Messenger(service);
      mManager = new SimWifiP2pManager(mService);
      mChannel = mManager.initialize(getApplication(), getMainLooper(), null);
      mBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
      mService = null;
      mManager = null;
      mChannel = null;
      mBound = false;
    }
  };
}
