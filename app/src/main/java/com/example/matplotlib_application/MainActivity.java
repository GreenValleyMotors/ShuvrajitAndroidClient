package com.example.matplotlib_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private int StoragePermissionCode= 1;

    Button button_upload_file, button_plot;
    TextView textView;
    Spinner x_axis_spinner, y_axis_spinner, z_axis_spinner, select_plot, viewing_angle;
    String folder_path, PATH, x_axis, y_axis, z_axis, str, SelectPlot, angle;
    int Angle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_upload_file= (Button)findViewById(R.id.upload_file_button);
        button_plot= (Button)findViewById(R.id.plot_button);
        textView= (TextView) findViewById(R.id.textview);
        x_axis_spinner= (Spinner) findViewById(R.id.x_axis);
        y_axis_spinner= (Spinner) findViewById(R.id.y_axis);
        z_axis_spinner= (Spinner) findViewById(R.id.z_axis);
        select_plot= (Spinner) findViewById(R.id.plot_select);
        viewing_angle= (Spinner) findViewById(R.id.angle);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,"You have already granted this permission!", Toast.LENGTH_SHORT).show();
        }else {
            requestStoragePermission();
        }

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Matplotlib_Application");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success
            folder_path= folder.getPath();
            Log.i("string path","folder_path: " +folder_path);
        } else {
            // Do something else on failure
        }

        final String[] plotting_style= new String[]{"Bar plot", "Histogram plot", "Pie plot", "Scatter 3D", "Line 3D"};
        final ArrayAdapter<String> adapter0 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, plotting_style);
        select_plot.setAdapter(adapter0);
        select_plot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectPlot= select_plot.getSelectedItem().toString();
                Log.i("string","select plotting graph: "+SelectPlot);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                myIntent.setType("*/*");
                startActivityForResult(myIntent, 100);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 100:
                if (resultCode == RESULT_OK) {
                    String convertedPath = null;
                    Uri path = data.getData();
                    //String Path= path.getPath();
                    textView.setText(path.toString());
                    Log.i("exel file", "path name: " + path);

                    FileUtils fu = new FileUtils(getApplicationContext());
                    PATH = fu.getPath(path);
                    ;//path.toString();//.getPath();
                    Log.d("exel file", "uri path name" + path);
                    Log.d("exel file", "real path name" + PATH);
                    //PATH= Path.substring(Path.indexOf(":")+1);
                }
                break;
        }

        if (!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("plotting_with_python");
        Log.d("exel file", "real path name" + PATH);
        PyObject obj1= pyobj.callAttr("readDataset",PATH);
        textView.setText(obj1.toString());
        PyObject obj2= pyobj.callAttr("columnName",PATH);
        final String[] columns= obj2.toJava(String[].class);
        Log.i("String Array","Columns: "+columns);
        final String[] select_angle= new String[]{"-180", "-150", "-135", "-120", "-90", "-60", "-45", "-30", "0"};
        /*final String[] plotting_style= new String[]{"Bar plot", "Histogram plot"};
        final ArrayAdapter<String> adapter0 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, plotting_style);
        select_plot.setAdapter(adapter0);
        select_plot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectPlot= select_plot.getSelectedItem().toString();
                Log.i("string","select plotting graph: "+SelectPlot);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        /*final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
        x_axis_spinner.setAdapter(adapter);
        x_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                x_axis= x_axis_spinner.getSelectedItem().toString();
                Log.i("string","x_axis of graph: "+x_axis);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
        y_axis_spinner.setAdapter(adapter1);
        y_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                y_axis= y_axis_spinner.getSelectedItem().toString();
                Log.i("string","y_axis of graph: "+y_axis);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("BarPlot", PATH, folder_path, x_axis, y_axis);
                str = obj.toString();
                openActivity2();
            }
        });*/
        if (SelectPlot=="Bar plot"){
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            x_axis_spinner.setAdapter(adapter);
            x_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    x_axis= x_axis_spinner.getSelectedItem().toString();
                    Log.i("string","x_axis of graph: "+x_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            y_axis_spinner.setAdapter(adapter1);
            y_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    y_axis= y_axis_spinner.getSelectedItem().toString();
                    Log.i("string","y_axis of graph: "+y_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            z_axis_spinner.setEnabled(false);
            button_plot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PyObject obj = pyobj.callAttr("BarPlot", PATH, folder_path, x_axis, y_axis);
                    str = obj.toString();
                    openActivity2();
                }
            });
        }else if (SelectPlot=="Histogram plot"){
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            x_axis_spinner.setAdapter(adapter);
            x_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    x_axis= x_axis_spinner.getSelectedItem().toString();
                    Log.i("string","x_axis of graph: "+x_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            y_axis_spinner.setEnabled(false);
            z_axis_spinner.setEnabled(false);
            button_plot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PyObject obj = pyobj.callAttr("histogram", PATH, folder_path, x_axis);
                    str = obj.toString();
                    openActivity2();
                }
            });
        }else if (SelectPlot=="Pie plot"){
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            x_axis_spinner.setAdapter(adapter);
            x_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    x_axis= x_axis_spinner.getSelectedItem().toString();
                    Log.i("string","x_axis of graph: "+x_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            y_axis_spinner.setAdapter(adapter1);
            y_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    y_axis= y_axis_spinner.getSelectedItem().toString();
                    Log.i("string","y_axis of graph: "+y_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            z_axis_spinner.setEnabled(false);
            button_plot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PyObject obj = pyobj.callAttr("PiePlot", PATH, folder_path, x_axis, y_axis);
                    str = obj.toString();
                    openActivity2();
                }
            });
        }else if (SelectPlot=="Scatter 3D"){
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            x_axis_spinner.setAdapter(adapter);
            x_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    x_axis= x_axis_spinner.getSelectedItem().toString();
                    Log.i("string","x_axis of graph: "+x_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            y_axis_spinner.setAdapter(adapter1);
            y_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    y_axis= y_axis_spinner.getSelectedItem().toString();
                    Log.i("string","y_axis of graph: "+y_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            z_axis_spinner.setAdapter(adapter2);
            z_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    z_axis= z_axis_spinner.getSelectedItem().toString();
                    Log.i("string","y_axis of graph: "+z_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, select_angle);
            viewing_angle.setAdapter(adapter3);
            viewing_angle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    angle= viewing_angle.getSelectedItem().toString();
                    Log.i("string","y_axis of graph: "+z_axis);
                    Angle= Integer.parseInt(angle);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            button_plot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PyObject obj = pyobj.callAttr("scatter3D", PATH, folder_path, x_axis, y_axis, z_axis, Angle);
                    str = obj.toString();
                    openActivity2();
                }
            });
        }else if (SelectPlot=="Line 3D"){
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            x_axis_spinner.setAdapter(adapter);
            x_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    x_axis= x_axis_spinner.getSelectedItem().toString();
                    Log.i("string","x_axis of graph: "+x_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            y_axis_spinner.setAdapter(adapter1);
            y_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    y_axis= y_axis_spinner.getSelectedItem().toString();
                    Log.i("string","y_axis of graph: "+y_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, columns);
            z_axis_spinner.setAdapter(adapter2);
            z_axis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    z_axis= z_axis_spinner.getSelectedItem().toString();
                    Log.i("string","y_axis of graph: "+z_axis);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, select_angle);
            viewing_angle.setAdapter(adapter3);
            viewing_angle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    angle= viewing_angle.getSelectedItem().toString();
                    Log.i("string","y_axis of graph: "+z_axis);
                    Angle= Integer.parseInt(angle);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            button_plot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PyObject obj = pyobj.callAttr("line3D", PATH, folder_path, x_axis, y_axis, z_axis, Angle);
                    str = obj.toString();
                    openActivity2();
                }
            });
        }

    }

    public void openActivity2(){
        Intent intent= new Intent(this, MainActivity2.class);
        intent.putExtra("str", str);
        startActivity(intent);
    }

    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed for access this application")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, StoragePermissionCode);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, StoragePermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == StoragePermissionCode) {
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();;
            }else{
                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}