package kz.sking.followers;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);

//        editText.setSelected(false);

        JSONFollowersTask task = new JSONFollowersTask();
        task.execute();
    }

    // Кнопка "Обновить"
    public void onClick(View view) {
        new JSONFollowersTask().execute();
    }

    // Класс отдельного асинхронного потока
    private class JSONFollowersTask extends AsyncTask<String, Void, followers> {

        // Тут реализуем фоновую асинхронную загрузку данных, требующих много времени
        @Override
        protected followers doInBackground(String... params) {
            if (editText.getText().toString().isEmpty()) {
                String name = "skingtop";
                return FollowersBuilder.buildFollowers(name);
            } else {
                String name = editText.getText().toString().trim();
                return FollowersBuilder.buildFollowers(name);
            }
//            return FollowersBuilder.buildFollowers(name);
        }
        // ----------------------------------------------------------------------------

        // Тут реализуем что нужно сделать после окончания загрузки данных
        @Override
        protected void onPostExecute(final followers followers) {
            super.onPostExecute(followers);


            imageView.post(new Runnable() { //  Используем синхронизацию с UI
                @Override
                public void run() {
                    // Если есть считанная иконка с web
                    if (followers.getIconData() != null) {
                        imageView.setImageBitmap(followers.getIconData()); // Установка иконки
                    } else {
                        imageView.setImageResource(R.mipmap.ic_launcher); // Установка своей иконки при ошибке
                    }
                    imageView.invalidate(); // Принудительная прорисовка картинки на всякий случай
                }
            });


            textView.post(new Runnable() { //  с использованием синхронизации UI
                @Override
                public void run() {
                    textView.setText("");
                    if (followers.getLogName(0) != null) {
                        if (editText.getText().toString().isEmpty()) {
                            String name = "skingtop";
                            textView.append(getString(R.string.title)+ " " + name +":\n\n");
                        } else {
                            String name = editText.getText().toString().trim();
                            textView.append(getString(R.string.title)+ " " + name +":\n\n");
                        }
                        for(int i = 0; i < 199;i++) {
                            if(followers.getLogName(i)!=null) {
                                textView.append("Login: " + followers.getLogName(i) + "\n");
                                textView.append("URL: " + followers.getLogUrl(i) + "\n\n");
                            }else{
                                break;
                            }
                        }
                    } else {
                        textView.append("Нет данных!" + "\n");
                        textView.append("Проверьте доступность Интернета");
                    }
                }
            });

        }
        // ------------------------------------------------------------------------------------

    }

}
