package br.com.kakobotasso.elcodechallenge;

import android.Manifest;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteOutOfMemoryException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;

public class MainActivity extends AppCompatActivity {

    boolean usaLocalizacao = false;
    private CheckBox checkLocalizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLocalizacao = (CheckBox) findViewById(R.id.localizacao_busca);
        checkLocalizacao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if( isChecked ){
                    if( temPermissaoLocalizacao() ){
                        usaLocalizacao = true;
                    } else {
                        pedePermissaoLocalizacao();
                    }
                }else{
                    usaLocalizacao = false;
                }
            }
        });
    }

    public void buscar(View v){
        System.out.println("REALIZAR BUSCA");
    }

    private boolean temPermissaoLocalizacao(){
        if(Permissive.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return true;
        }

        return false;
    }

    private void pedePermissaoLocalizacao(){
        new Permissive.Request(Manifest.permission.ACCESS_FINE_LOCATION)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        usaLocalizacao = true;
                    }
                })
                .whenPermissionsRefused(new PermissionsRefusedListener() {
                    @Override
                    public void onPermissionsRefused(String[] permissions) {
                        exibeAlertPermissaoNegada();
                    }
                })
                .execute(this);
    }

    private void exibeAlertPermissaoNegada(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_titulo))
                .setMessage(getString(R.string.alert_descricao))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkLocalizacao.toggle();
                    }
                })
                .setNegativeButton(getString(R.string.tentar_novamente), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pedePermissaoLocalizacao();
                    }
                })
                .show();
    }
}
