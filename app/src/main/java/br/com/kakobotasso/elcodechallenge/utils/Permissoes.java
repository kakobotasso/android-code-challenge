package br.com.kakobotasso.elcodechallenge.utils;

import android.Manifest;
import android.content.Context;

import com.github.jksiezni.permissive.Permissive;

public class Permissoes {
    Context contexto;

    public Permissoes(Context contexto){
        this.contexto = contexto;
    }

    public boolean temPermissaoLocalizacao(){
        if(Permissive.checkPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return true;
        }

        return false;
    }
}
