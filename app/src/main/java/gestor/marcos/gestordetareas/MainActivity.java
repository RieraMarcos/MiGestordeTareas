package gestor.marcos.gestordetareas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener{

    @NotEmpty(messageResId = R.string.vac_name )
    EditText nombreEditText;
    @NotEmpty(messageResId = R.string.vac_email )
    @Email(messageResId = R.string.email)
    EditText emailEditText;
    Button guardarButton;
    private Validator validator;

    ListView UsuarioListView;
    List<String> usuarios = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //cast and upcast
        nombreEditText = (EditText) findViewById(R.id.editTextNombre);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        guardarButton = (Button) findViewById(R.id.buttonGuardar);

        UsuarioListView = (ListView) findViewById(R.id.listViewUsuarios);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,usuarios);

        UsuarioListView.setAdapter(adapter);

        String nombre = nombreEditText.getText().toString();



        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = nombreEditText.getText().toString();
                if (nombre.length()<3){
                    Toast.makeText(MainActivity.this, R.string.min_char, Toast.LENGTH_SHORT).show();
                    nombreEditText.requestFocus();
                }else {
                    validator.validate();
                    String datos = nombreEditText+" "+emailEditText;
                    usuarios.add(datos);
                    adapter.notifyDataSetChanged();
                }

            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, R.string.datos_ok, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
            else
            {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }


        }
    }

    public void Nombre() {

    }
}
