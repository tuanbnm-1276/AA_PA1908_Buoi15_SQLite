package vn.sunasterisk.buoi15_sqlite.screen.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.sunasterisk.buoi15_sqlite.R;
import vn.sunasterisk.buoi15_sqlite.data.database.NewsDatabaseHelper;
import vn.sunasterisk.buoi15_sqlite.data.model.New;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mTextId;
    private EditText mTextTitle;
    private EditText mTextContent;

    private Button mButtonInsertNew;
    private Button mButtonUpdateNew;
    private Button mButtonDeleteNew;
    private Button mButtonSelectAllNews;
    private Button mButtonDeleteDb;
    private Button mButtonSelectNew;

    private RecyclerView mRecyclerNews;

    private List<New> mNews;

    private NewsDatabaseHelper mNewsDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        registerListeners();
    }

    private void registerListeners() {
        mButtonInsertNew.setOnClickListener(this);
        mButtonUpdateNew.setOnClickListener(this);
        mButtonDeleteNew.setOnClickListener(this);
        mButtonSelectAllNews.setOnClickListener(this);
        mButtonDeleteDb.setOnClickListener(this);
        mButtonSelectNew.setOnClickListener(this);
    }

    private void initComponents() {
        mTextId = findViewById(R.id.text_id);
        mTextTitle = findViewById(R.id.text_title);
        mTextContent = findViewById(R.id.text_content);

        mButtonInsertNew = findViewById(R.id.button_insert_new);
        mButtonUpdateNew = findViewById(R.id.button_update_new);
        mButtonDeleteNew = findViewById(R.id.button_delete_new);
        mButtonSelectAllNews = findViewById(R.id.button_select_news);
        mButtonDeleteDb = findViewById(R.id.button_delete_db);
        mButtonSelectNew = findViewById(R.id.button_select_new);

        mRecyclerNews = findViewById(R.id.recycler_news);

        mNewsDatabaseHelper = new NewsDatabaseHelper(this);
        mNews = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_insert_new:
                insertNew();
                break;
            case R.id.button_update_new:
                updateNew();
                break;
            case R.id.button_delete_new:
                deleteNew();
                break;
            case R.id.button_select_news:
                selectAllNews();
                break;
            case R.id.button_delete_db:
                deleteDb();
                break;
            case R.id.button_select_new:
                selectNew();
                break;
            default:
                break;
        }
    }

    private void selectNew() {
        String idStr = mTextId.getText().toString();
        int id = Integer.parseInt(idStr);
        New aNew = mNewsDatabaseHelper.selectNew(id);

        if (aNew == null) {
            Toast.makeText(this, "Has not New with ID = " + id, Toast.LENGTH_SHORT).show();
            return;
        }

        mTextTitle.setText(aNew.getTitle());
        mTextContent.setText(aNew.getContent());

    }

    private void deleteDb() {
    }

    private void selectAllNews() {
        mNews.clear();
        mNews.addAll(mNewsDatabaseHelper.getAllNews());

        Log.d(TAG, "selectAllNews: Size = " + mNews.size());

        for (int i = 0; i < mNews.size(); i++) {
            Log.d(TAG, "selectAllNews: " + mNews.get(i).toString());
        }
    }

    private void deleteNew() {
        String idStr = mTextId.getText().toString();
        int id = Integer.parseInt(idStr);
        mNewsDatabaseHelper.deleteNew(id);
    }

    private void updateNew() {
        String idStr = mTextId.getText().toString();
        int id = Integer.parseInt(idStr);
        String newTitle = mTextTitle.getText().toString();
        String newContent = mTextContent.getText().toString();
        mNewsDatabaseHelper.updateNew(id, newTitle, newContent);
    }

    private void insertNew() {
        String title = mTextTitle.getText().toString();
        String content = mTextContent.getText().toString();
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and Content Not Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        New aNew = new New(title, content);
        mNewsDatabaseHelper.insertNew(aNew);
    }
}
