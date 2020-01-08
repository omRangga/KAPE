package com.unpas.chatak;

import android.widget.TextView;

public class UserAddressModel {
    TextView nama;
    TextView alamat;
    TextView kode_pos;
    TextView no_hp;

    public UserAddressModel(TextView nama, TextView alamat, TextView kode_pos, TextView no_hp){
        this.nama=nama;
        this.alamat=alamat;
        this.kode_pos=kode_pos;
        this.no_hp=no_hp;
    }
    public UserAddressModel(){}

    public TextView getNama() {
        return nama;
    }

    public void setNama(TextView nama) {
        this.nama = nama;
    }

    public TextView getAlamat() {
        return alamat;
    }

    public void setAlamat(TextView alamat) {
        this.alamat = alamat;
    }

    public TextView getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(TextView kode_pos) {
        this.kode_pos = kode_pos;
    }
    public TextView getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(TextView no_hp) {
        this.no_hp = no_hp;
    }
}
