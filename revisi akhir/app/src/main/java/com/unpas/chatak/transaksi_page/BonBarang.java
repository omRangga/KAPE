package com.unpas.chatak.transaksi_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.unpas.chatak.MyCartFragment;
import com.unpas.chatak.R;
import com.unpas.chatak.main_page.MainActivity;
import com.unpas.chatak.main_page.PembayaranActivity;
import com.unpas.chatak.model.database.order_history.OrderHistoryPojo;

import static com.unpas.chatak.main_page.MainActivity.db;

public class BonBarang extends AppCompatActivity {
    private Button btBayar, btKeranjang;
    private TextView txJmlHalaman, txPilihWarna, txJenisKertas, txUkuranKertas, txSisi, txOrientasi, txPilihanJilid, txJmlCetak, txWaktuProses, txPosisiJilid, txJenisLaminasi, txCoverDepan, txDuaTiga, txTigaEmpat, txEmpatEnam, txFrame, txPanjang, txLebar, txJenisBahan, txFinishing, txWarnaCetak, txLipatan, txBajuXs, txBajuS, txBajuM, txBajuXl, txBajuXxl, txJenisCetak, txJumlah, txWarna, txUkuranBaju, txWarnaBahan, txUkuranBahan, txBahan, txCoverBelakang, txHargaSatuan, txHargaTotal, txDetilWarna, txWarnaFrame;
    private TableRow rowJmlHalaman, rowPilihWarna, rowJenisKertas, rowUkuranKertas, rowSisi, rowOrientasi, rowPilihanJilid, rowJmlCetak, rowWaktuProses, rowPosisiJilid, rowJenisLaminasi, rowCoverDepan, rowDuaTiga, rowTigaEmpat, rowEmpatEnam, rowFrame, rowPanjang, rowLebar, rowJenisBahan, rowFinishing, rowWarnaCetak, rowLipatan, rowBajuXs, rowBajuS, rowBajuM, rowBajuXl, rowBajuXxl, rowJenisCetak, rowJumlah, rowWarna, rowUkuranBaju, rowWarnaBahan, rowUkuranBahan, rowBahan, rowCoverBelakang, rowHargaSatuan, rowHargaTotal, rowDetilWarna, rowWarnaFrame;
    private TextView namaBarang;
    private String sBarang;
    private String jmlData;
    private String[] dataBarang;
    private TextView[] txBarang;
    private TableRow[] trBarang;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    final String TAG = "Bon Barang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bon_barang);
        //menampilkan data dari MasukPesanan
//        tampilDataKonfirmasi();
        showDataKonfirmasi();
        //kembali ke halaman sebelumnya
        ImageButton back = findViewById(R.id.btBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BonBarang.super.onBackPressed();
            }
        });
        //end back
        //add barang ke DB
        btKeranjang = findViewById(R.id.bt_tambah_keranjang);
        btKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("add database judul", sBarang);
                for (int i = 0; i < Integer.parseInt(jmlData); i++) {
                    //hapus komentar dibawah untuk tambah barang ke database
                    //getSpekBarang();
                    Log.d("add database isi" + i, getSpekBarang()[i]);
                }
                Intent pesananBarang = new Intent(BonBarang.this, MainActivity.class);
                OrderHistoryPojo data = new OrderHistoryPojo(namaBarang.getText().toString());
                db.historyOrderDao().insertHistory(data);
                startActivity(pesananBarang);
            }
        });

        btBayar = findViewById(R.id.bt_konfirmasi_barang);
        btBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent bayar = new Intent(BonBarang.this, PembayaranActivity.class);
               startActivity(bayar);
            }
        });
    }

    //method untuk meneruskan data barang ke DATABASE
    private String[] getSpekBarang() {
        return dataBarang;
    }

    //method untuk mendefinisikan TableRow pada BonBarang
    private void setRowBarang() {
        rowJmlHalaman = findViewById(R.id.rowJmlHalaman);
        rowPilihWarna = findViewById(R.id.rowPilihWarna);
        rowJenisKertas = findViewById(R.id.rowJenisKertas);
        rowUkuranKertas = findViewById(R.id.rowUkuranKertas);
        rowSisi = findViewById(R.id.rowSisi);
        rowOrientasi = findViewById(R.id.rowOrientasi);
        rowPilihanJilid = findViewById(R.id.rowPilihanJilid);
        rowPosisiJilid = findViewById(R.id.rowPosisiJilid);
        rowJenisLaminasi = findViewById(R.id.rowJenisLaminasi);
        rowCoverDepan = findViewById(R.id.rowCoverDepan);
        rowDuaTiga = findViewById(R.id.rowFotoDuaTiga);
        rowTigaEmpat = findViewById(R.id.rowFotoTigaEmpat);
        rowEmpatEnam = findViewById(R.id.rowFotoEmpatEnam);
        rowFrame = findViewById(R.id.rowFrame);
        rowPanjang = findViewById(R.id.rowPanjang);
        rowLebar = findViewById(R.id.rowLebar);
        rowJenisBahan = findViewById(R.id.rowJenisBahan);
        rowUkuranBahan = findViewById(R.id.rowUkuranBahan);
        rowFinishing = findViewById(R.id.rowFinishing);
        rowWarnaCetak = findViewById(R.id.rowWarnaCetak);
        rowLipatan = findViewById(R.id.rowLipatan);
        rowJmlCetak = findViewById(R.id.rowJmlCetak);
        rowWaktuProses = findViewById(R.id.rowWaktuProses);
        rowWarna = findViewById(R.id.rowWarna);
        rowBajuXs = findViewById(R.id.rowBajuXs);
        rowBajuS = findViewById(R.id.rowBajuS);
        rowBajuM = findViewById(R.id.rowBajuM);
        rowBajuXl = findViewById(R.id.rowBajuXl);
        rowBajuXxl = findViewById(R.id.rowBajuXxl);
        rowJenisCetak = findViewById(R.id.rowJenisCetak);
        rowJumlah = findViewById(R.id.rowJml);
        rowUkuranBaju = findViewById(R.id.rowUkuranBaju);
        rowWarnaBahan = findViewById(R.id.rowWarnaBahan);
        rowBahan = findViewById(R.id.rowBahan);
        rowCoverBelakang = findViewById(R.id.rowCoverBelakang);
        rowDetilWarna = findViewById(R.id.rowDetilWarna);
        rowHargaSatuan = findViewById(R.id.rowHargaSatuan);
        rowHargaTotal = findViewById(R.id.rowHargaTotal);
        rowWarnaFrame = findViewById(R.id.rowWarnaFrame);

    }

    //method untuk mendefinisikan TextView pada BonBarang
    private void setValueBarang() {
        txJmlHalaman = findViewById(R.id.ringkasan_jml_halaman);
        txPilihWarna = findViewById(R.id.ringkasan_pilih_warna);
        txUkuranKertas = findViewById(R.id.ringkasan_ukuran_kertas);
        txJenisKertas = findViewById(R.id.ringkasan_jenis_kertas);
        txSisi = findViewById(R.id.ringkasan_sisi);
        txOrientasi = findViewById(R.id.ringkasan_orientasi);
        txPilihanJilid = findViewById(R.id.ringkasan_pilihan_jilid);
        txPosisiJilid = findViewById(R.id.ringkasan_posisi_jilid);
        txJenisLaminasi = findViewById(R.id.ringkasan_jenis_laminasi);
        txCoverDepan = findViewById(R.id.ringkasan_cover_depan);
        txDuaTiga = findViewById(R.id.ringkasan_foto_dua_tiga);
        txTigaEmpat = findViewById(R.id.ringkasan_foto_tiga_empat);
        txEmpatEnam = findViewById(R.id.ringkasan_foto_empat_enam);
        txFrame = findViewById(R.id.ringkasan_frame);
        txPanjang = findViewById(R.id.ringkasan_panjang);
        txLebar = findViewById(R.id.ringkasan_lebar);
        txJenisBahan = findViewById(R.id.ringkasan_jenis_bahan);
        txUkuranBahan = findViewById(R.id.ringkasan_ukuran_bahan);
        txFinishing = findViewById(R.id.ringkasan_finishing);
        txWarnaCetak = findViewById(R.id.ringkasan_warna_cetak);
        txLipatan = findViewById(R.id.ringkasan_lipatan);
        txJmlCetak = findViewById(R.id.ringkasan_jumlah_cetak);
        txWaktuProses = findViewById(R.id.ringkasan_waktu_proses);
        txWarna = findViewById(R.id.ringkasan_warna);
        txBajuXs = findViewById(R.id.ringkasan_baju_xs);
        txBajuS = findViewById(R.id.ringkasan_baju_s);
        txBajuM = findViewById(R.id.ringkasan_baju_m);
        txBajuXl = findViewById(R.id.ringkasan_baju_xl);
        txBajuXxl = findViewById(R.id.ringkasan_baju_xxl);
        txJenisCetak = findViewById(R.id.ringkasan_jenis_cetak);
        txJumlah = findViewById(R.id.ringkasan_jumlah);
        txUkuranBaju = findViewById(R.id.ringkasan_ukuran_baju);
        txWarnaBahan = findViewById(R.id.ringkasan_warna_bahan);
        txBahan = findViewById(R.id.ringkasan_bahan);
        txCoverBelakang = findViewById(R.id.ringkasan_cover_belakang);
        txDetilWarna = findViewById(R.id.ringkasan_detil_warna);
        txHargaSatuan = findViewById(R.id.ringkasan_harga_satuan);
        txHargaTotal = findViewById(R.id.ringkasan_harga_total);
        txWarnaFrame = findViewById(R.id.ringkasan_warna_frame);
    }

    //method untuk memasukan data dari MasukPesanan ke TextView
    private void setSpekBarang(TableRow[] trBarang, TextView[] txBarang) {
        //get data jumlah data
        jmlData = getIntent().getStringExtra("JumlahData");
        // Set header menu
        namaBarang.setText(sBarang);
        //set Konfirmasi data barang
        Intent isiBarang = getIntent();
        dataBarang = isiBarang.getStringArrayExtra("DataBarang");
        for (int i = 0; i < Integer.parseInt(jmlData); i++) {
            //verifikasi input barang 0 atau isEmpty()
            if (dataBarang[i].isEmpty() || dataBarang[i].substring(0, 1).equalsIgnoreCase("0")) {
                //handle error isEmpty() dataBarang
                dataBarang[i] = "0";
                //end handle
                Log.d("isi barang KOSONG", dataBarang[i]);
                Toast.makeText(this, "Data Pesanan Belum Sesuai", Toast.LENGTH_SHORT).show();
                finish();
            }
            //menerima data barang yang sesuai
            else {
                trBarang[i].setVisibility(View.VISIBLE);
                txBarang[i].setText(dataBarang[i]);
                Log.d("isi Barang", dataBarang[i]);
            }
        }
    }

    ////method untuk menampilkan data yang telah dimasukan pengguna
    private void showDataKonfirmasi() {
        setRowBarang();
        setValueBarang();
        namaBarang = findViewById(R.id._ringkasa_nama_barang);
        //get data nama barang
        sBarang = getIntent().getStringExtra("NamaBarang");
        switch (sBarang) {
//          ----------------------MEMUNCULKAN BON YANG HEADERNYA KATEGORI DOKUMEN-----------------------------------
            case "Laporan":
                //get data jumlah data
                jmlData = getIntent().getStringExtra("JumlahData");
                if (jmlData.equalsIgnoreCase("12")) {
                    trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowJenisKertas, rowUkuranKertas, rowSisi, rowOrientasi,
                            rowPilihanJilid, rowPosisiJilid, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                            txPilihanJilid, txPosisiJilid, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                } else if (jmlData.equalsIgnoreCase("14")) {
                    trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowJenisKertas, rowUkuranKertas, rowSisi, rowOrientasi,
                            rowPilihanJilid, rowPosisiJilid, rowCoverDepan, rowCoverBelakang, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                            txPilihanJilid, txPosisiJilid, txCoverDepan, txCoverBelakang, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                } else {
                    trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowJenisKertas, rowUkuranKertas, rowSisi, rowOrientasi,
                            rowPilihanJilid, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                            txPilihanJilid, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                }
                break;
            case "Buku":
                trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowJenisKertas, rowUkuranKertas, rowSisi, rowOrientasi,
                        rowPilihanJilid, rowPosisiJilid, rowJenisLaminasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                        txPilihanJilid, txPosisiJilid, txJenisLaminasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Berkas":
                jmlData = getIntent().getStringExtra("JumlahData");
                if (jmlData.equalsIgnoreCase("12")) {
                    trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowJenisKertas, rowUkuranKertas, rowSisi, rowOrientasi,
                            rowPilihanJilid, rowPosisiJilid, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                            txPilihanJilid, txPosisiJilid, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                } else {
                    trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowJenisKertas, rowUkuranKertas, rowSisi, rowOrientasi,
                            rowPilihanJilid, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                            txPilihanJilid, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                }
                break;
            case "Sertifikat":
                trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowSisi, rowOrientasi,
                        rowJenisLaminasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                        txJenisLaminasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Portofolio":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowOrientasi,
                        rowPilihanJilid, rowPosisiJilid, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                        txPilihanJilid, txPosisiJilid, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Skripsi":
                trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowUkuranKertas, rowJenisKertas, rowSisi,
                        rowOrientasi, rowPilihanJilid, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txSisi,
                        txOrientasi, txPilihanJilid, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Buku Lipat":
                trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowUkuranKertas, rowJenisKertas, rowSisi, rowOrientasi,
                        rowPilihanJilid, rowPosisiJilid, rowCoverDepan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txOrientasi,
                        txPilihanJilid, txPosisiJilid, txCoverDepan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Notebook":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowPilihanJilid, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txPilihanJilid, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Nota":
                trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowUkuranKertas, rowJenisKertas, rowOrientasi, rowPilihanJilid, rowCoverDepan, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txOrientasi, txPilihanJilid, txCoverDepan, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Kop Surat":
                trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowUkuranKertas, rowJenisKertas, rowCoverDepan, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txUkuranKertas, txJenisKertas, txCoverDepan, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Map":
                trBarang = new TableRow[]{rowPilihWarna, rowUkuranKertas, rowJenisKertas, rowSisi, rowJenisLaminasi, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txJenisLaminasi, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Amplop":
                trBarang = new TableRow[]{rowJmlHalaman, rowPilihWarna, rowJenisKertas, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txPilihWarna, txJenisKertas, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Pas Foto":
                trBarang = new TableRow[]{rowDuaTiga, rowTigaEmpat, rowEmpatEnam, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txDuaTiga, txTigaEmpat, txEmpatEnam, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Foto":
                jmlData = getIntent().getStringExtra("JumlahData");
                if (jmlData.equalsIgnoreCase("7")) {
                    trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowFrame, rowWarnaFrame, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txFrame, txWarnaFrame, txWaktuProses, txHargaSatuan, txHargaTotal};
                } else {
                    trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowFrame, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txFrame, txWaktuProses, txHargaSatuan, txHargaTotal};
                }
                break;
//              ------------------------------END KATEGORI DOKUMEN----------------------------------
//              ------------------------------KATEGORI MEDIA PROMOSI----------------------------------
            case "Name Tag":
                trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowSisi, rowOrientasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txSisi, txOrientasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Kartu Nama":
                trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowSisi, rowJenisLaminasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txSisi, txJenisLaminasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Spanduk":
                trBarang = new TableRow[]{rowPanjang, rowLebar, rowJenisBahan, rowFinishing, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txPanjang, txLebar, txJenisBahan, txFinishing, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Poster":
                trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowSisi, rowJenisLaminasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txSisi, txJenisLaminasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Stand Banner":
                trBarang = new TableRow[]{rowSisi, rowJenisBahan, rowUkuranBahan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txSisi, txJenisBahan, txUkuranBahan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Flyer Ekonomis":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowWarnaCetak, rowLipatan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txWarnaCetak, txLipatan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Brosur Ekonomis":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowWarnaCetak, rowLipatan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txWarnaCetak, txLipatan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Baliho":
                trBarang = new TableRow[]{rowPanjang, rowLebar, rowJenisBahan, rowFinishing, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txPanjang, txLebar, txJenisBahan, txFinishing, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Booklet":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowOrientasi, rowPilihanJilid,
                        rowPosisiJilid, rowJenisLaminasi, rowCoverDepan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txOrientasi, txPilihanJilid,
                        txPosisiJilid, txJenisLaminasi, txCoverDepan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Katalog":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowOrientasi, rowPilihanJilid,
                        rowPosisiJilid, rowJenisLaminasi, rowCoverDepan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txOrientasi, txPilihanJilid,
                        txPosisiJilid, txJenisLaminasi, txCoverDepan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Kain":
                trBarang = new TableRow[]{rowPanjang, rowLebar, rowJenisBahan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txPanjang, txLebar, txJenisBahan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Bendera":
                trBarang = new TableRow[]{rowPanjang, rowLebar, rowJenisBahan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txPanjang, txLebar, txJenisBahan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Poster Hitam Putih":
                trBarang = new TableRow[]{rowPilihWarna, rowUkuranKertas, rowJenisKertas, rowSisi, rowOrientasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txPilihWarna, txUkuranKertas, txJenisKertas, txSisi, txOrientasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Poster Standard":
                trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowSisi, rowOrientasi, rowJenisLaminasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txSisi, txOrientasi, txJenisLaminasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Brosur Hitam Putih":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowWarnaCetak, rowLipatan, rowOrientasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txWarnaCetak, txLipatan, txOrientasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Flyer Hitam Putih":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowWarnaCetak, rowLipatan, rowOrientasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txWarnaCetak, txLipatan, txOrientasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Brosur":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowWarnaCetak, rowLipatan, rowOrientasi, rowJenisLaminasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txWarnaCetak, txLipatan, txOrientasi, txJenisLaminasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Flyer Standard":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowSisi, rowWarnaCetak, rowLipatan, rowOrientasi, rowJenisLaminasi, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txSisi, txWarnaCetak, txLipatan, txOrientasi, txJenisLaminasi, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Stempel":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowWarnaCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txWarnaCetak, txWaktuProses, txHargaSatuan, txHargaTotal, txHargaSatuan, txHargaTotal};
                break;
            case "Cutting Sticker":
                trBarang = new TableRow[]{rowJmlHalaman, rowUkuranKertas, rowJenisKertas, rowWarnaCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJmlHalaman, txUkuranKertas, txJenisKertas, txWarnaCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Sticker":
                trBarang = new TableRow[]{rowJenisKertas, rowJenisLaminasi, rowPanjang, rowLebar, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJenisKertas, txJenisLaminasi, txPanjang, txLebar, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "ID Card":
                //isi data
                break;
            case "Tali ID Card":
                //isi data
                break;
            case "Topi":
                trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Kaos Satuan":
                jmlData = getIntent().getStringExtra("JumlahData");
                Log.d("isi DATA KAOS", jmlData);
                if (jmlData.equalsIgnoreCase("11")) {
                    trBarang = new TableRow[]{rowWarna, rowDetilWarna, rowJenisLaminasi, rowJenisBahan, rowBajuXs, rowBajuS, rowBajuM,
                            rowBajuXl, rowBajuXxl, rowJenisCetak, rowJumlah, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txWarna, txDetilWarna, txJenisLaminasi, txJenisBahan, txBajuXs, txBajuS, txBajuM, txBajuXl,
                            txBajuXxl, txJenisCetak, txJumlah, txWaktuProses, txHargaSatuan, txHargaTotal};
                } else {
                    trBarang = new TableRow[]{rowWarna, rowJenisLaminasi, rowJenisBahan, rowBajuXs, rowBajuS, rowBajuM,
                            rowBajuXl, rowBajuXxl, rowJenisCetak, rowJumlah, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txWarna, txJenisLaminasi, txJenisBahan, txBajuXs, txBajuS, txBajuM, txBajuXl,
                            txBajuXxl, txJenisCetak, txJumlah, txWaktuProses, txHargaSatuan, txHargaTotal};
                }
                break;
            case "Kaos Standard":
                trBarang = new TableRow[]{rowWarna, rowJenisLaminasi, rowJenisBahan, rowUkuranBaju, rowJenisCetak, rowJumlah, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txWarna, txJenisLaminasi, txJenisBahan, txUkuranBaju, txJenisCetak, txJumlah, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Kaos Ekonomis":
                trBarang = new TableRow[]{rowWarna, rowJenisLaminasi, rowJenisBahan, rowUkuranBaju, rowJenisCetak, rowJumlah, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txWarna, txJenisLaminasi, txJenisBahan, txUkuranBaju, txJenisCetak, txJumlah, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
//              ------------------------------END KATEGORI MEDIA PROMOSI----------------------------------
//                       ------------------------- KATEGORI SOUVENIR--------------------------------------
            case "Mug":
                //get data jumlah data
                jmlData = getIntent().getStringExtra("JumlahData");
                if (jmlData.equalsIgnoreCase("5")) {
                    trBarang = new TableRow[]{rowJenisBahan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txJenisBahan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                } else {
                    trBarang = new TableRow[]{rowJenisBahan, rowWarnaBahan, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                    txBarang = new TextView[]{txJenisBahan, txWarnaBahan, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                }
                break;
            case "Bolpoin":
                trBarang = new TableRow[]{rowUkuranBahan, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranBahan, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Pin":
                trBarang = new TableRow[]{rowJenisBahan, rowBahan, rowJumlah, rowFinishing, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJenisBahan, txBahan, txJumlah, txFinishing, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Gantungan kunci":
                trBarang = new TableRow[]{rowSisi, rowJenisBahan, rowBahan, rowJumlah, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txSisi, txJenisBahan, txBahan, txJumlah, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Tumbler":
                trBarang = new TableRow[]{rowUkuranBahan, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranBahan, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Gantungan kunci Akrilik":
                trBarang = new TableRow[]{rowUkuranBahan, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranBahan, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Kalender Dinding":
                trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Kalender Meja":
                trBarang = new TableRow[]{rowUkuranKertas, rowJenisKertas, rowJmlCetak, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranKertas, txJenisKertas, txJmlCetak, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Payung":
                trBarang = new TableRow[]{rowJenisBahan, rowFinishing, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txJenisBahan, txFinishing, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Jam Dinding":
                trBarang = new TableRow[]{rowUkuranBahan, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranBahan, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Tas Kresek":
                trBarang = new TableRow[]{rowUkuranBahan, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranBahan, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
            case "Kipas":
                trBarang = new TableRow[]{rowUkuranBahan, rowWaktuProses, rowHargaSatuan, rowHargaTotal};
                txBarang = new TextView[]{txUkuranBahan, txWaktuProses, txHargaSatuan, txHargaTotal};
                break;
//              -------------------------END KATEGORI SOUVENIR------------------------------------
        }
        //mengeset row barang dan value yang muncul
        setSpekBarang(trBarang, txBarang);
    }


}
