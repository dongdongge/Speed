package soyouarehere.imwork.speed.app.async_task;

import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<Params,Progress,Result> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Result doInBackground(Params... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
    }
}
