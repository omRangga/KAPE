package com.unpas.chatak.transaksi_page;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.unpas.chatak.R;

import java.util.Arrays;
import java.util.List;

public class MasukPesanan extends AppCompatActivity {
   //reusable key untuk intent
    public static final String PESANAN_MASUK = "TerimaPesanan";
    private String keyUpload;
    //memanggil kalkulator barang
    private KalkulatorBarang kalkulatorBarang = new KalkulatorBarang();
    //  Mendefinisikan bar pada kategori
    private LinearLayout barJmlHalaman, barJmlHalamanSpinner, barPilihWarna, barJenisKertas, barUkuranKertas, barSisi, barOrientasi, barPilihanJilid, barJmlCetak, barWaktuProses, barPosisiJilid, barJenisLaminasi, barCoverDepan, barDuaTiga, barTigaEmpat, barEmpatEnam, barFrame, barPanjang, barLebar, barJenisBahan, barFinishing, barWarnaCetak, barLipatan, barBajuXs, barBajuS, barBajuM, barbajuXl, barBajuXxl, barJenisCetak, barJumlah, barWarna, barUkuranBaju, barWarnaBahan, barUkuranBahan, barBahan, barCoverBelakang, barDetilWarna, barWarnaFrame;
    //    Mendapatkan Judul Kategori
    private TextView pesananMuncul, txtCoverDepan, txtJenisLaminasi, txtJenisBahan, txtFormat, txtHargaSatuan, txtHargaTotal;
    ImageButton btBack;
    //    Redirect footer button
    private Button btKonfirmasiBarang, btUpload, btVallet;
    //  Set Variabel untuk menerima Header
    private String pesananMasuk;
    private String jmlData;
    //array data
    private int[] isi;
    private String[] spesifikasiBarang;
    private int[] resourceKategoriMuncul;
    private LinearLayout[] barKategoriMuncul;
    private Spinner[] spinnerKategoriMuncul;
    private Spinner[] spinners;
    //set variabel untuk mengirim ringkasan barang
    private String sJmlHalaman, sJmlHalamanSpinner, sPilihWarna, sJenisKertas, sUkuranKertas, sSisi, sOrientasi, sPilihanJilid, sJmlCetak, sWaktuProses, sPosisiJilid, sJenisLaminasi, sCoverDepan, sDuaTiga, sTigaEmpat, sEmpatEnam, sFrame, sPanjang, sLebar, sJenisBahan, sFinishing, sWarnaCetak, sLipatan, sBajuXs, sBajuS, sBajuM, sbajuXl, sBajuXxl, sJenisCetak, sJumlah, sWarna, sUkuranBaju, sWarnaBahan, sUkuranBahan, sBahan, sDetilWarna, sCoverBelakang, sHargaSatuan, sHargaTotal, sWarnaFrame;
    //  Mendefinisikan inputan pada setiap bar kategori
    private EditText edtjmlHalaman, edtjmlCetak, edtPanjang, edtLebar, edtJumlah,
            edtBajuXs, edtBajuS, edtBajuM, edtBajuXl, edtBajuXxl;
    private Spinner spinJmlHalaman, spinPilihWarna, spinJenisKertas, spinUkuranKertas,
            spinSisi, spinOrientasi, spinPilihanJilid, spinWaktuProses,
            spinPosisiJilid, spinJenisLaminasi, spinCoverDepan,
            spinFotoDuaTiga, spinFotoTigaEmpat, spinFotoEmpatEnam,
            spinFrame, spinJenisBahan, spinFinishing, spinWarnaCetak,
            spinLipatan, spinJenisCetak, spinWarna, spinUkuranBaju,
            spinWarnaBahan, spinUkuranBahan, spinBahan, spinCoverBelakang, spinDetilWarna, spinWarnaFrame;
    //  Mendefinisikan variabel untuk membantu pengelolaan bar kategori
    private int muncul = View.VISIBLE;
    //Program Berjalan
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_pesanan);
        pesananMuncul = findViewById(R.id.header_masuk_pesanan);
        // menerima kiriman kategori
        pesananMasuk = getIntent().getStringExtra(PESANAN_MASUK);
        // Set header menu
        pesananMuncul.setText(pesananMasuk);
        //end direct halaman bon barang
        //mengarahkan ke API Vallet
        btVallet = findViewById(R.id.btnApiVallet);
        btUpload = findViewById(R.id.bt_upload_file);
        //aksi button untuk mengarahkan ke desain API VALLET
        btVallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://app.vallet.id"));
                startActivity(viewIntent);
            }
        });
        //aksi button untuk upload file
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent();
                upload.setType(keyUpload);
                upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(upload, "UPLOAD FILE"), 1);
            }
        });
        //back button header
        btBack = findViewById(R.id.btBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitBackKey();
            }
        });
        //memasukan nilai bar dan value kategori
        //deklarasi barKategori
        setShowBar();
        //deklarasi spinnerKategori
        setValueKategori();
        //deklarasi subHeader
        setKomponenHeader();
        //end nilai bar dan value kategori
        //Set visible bar kategori setiap barang(MEMUNCULKAN BAR PADA SETIAP KATEGORI)
        munculKomponenBarang();
        //end set visible bar kategori barang
        // mengarahkan ke halaman bon barang
        btKonfirmasiBarang = findViewById(R.id.bt_cetak_file);
        btKonfirmasiBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//      (MENYIMPAN SPESIFIKASI BARANG PADA SETIAP KATEGORI)
                setValueInput();
                //mengarahkan ke halaman BonBarang
                Intent barangKonfirmasi = new Intent(MasukPesanan.this, BonBarang.class);
                //menyimpan data yang telah dikonfirmasi
                setKonfirmasiData();
                simpanPesanan(spesifikasiBarang);
                //mengeset jumlah data yang dikirim
                jmlData = String.valueOf(spesifikasiBarang.length);
                //menyimpan kunci untuk di kirim ke BonBarang
                barangKonfirmasi.putExtra("NamaBarang", pesananMasuk);
                barangKonfirmasi.putExtra("JumlahData", jmlData);
                barangKonfirmasi.putExtra("DataBarang", spesifikasiBarang);
                //Log konfirmasi barang
                Log.d("isi_Nama_Barang", pesananMasuk);
                Log.d("isi_Jumlah_Data", jmlData);
                //membuka Halaman BonBarang
                startActivity(barangKonfirmasi);
            }
        });
    }

    //method untuk mengganti jumlah pada bar Kategori, ex : jml halaman, dll
    private void changeCount(String value, EditText bar) {
        bar.setText(value);
        listenCount(bar);
    }

    //create bar
    private void createBar(int jmlBar, LinearLayout[] bar) {
        this.barKategoriMuncul = bar;
        for (int i = 0; i < jmlBar; i++) {
            bar[i].setVisibility(muncul);
        }
    }

    //method untuk create value pada kategori(khusus spinner, pada editText menggunakan changeCount();
    private void createValue(int jmlSpinner, final Spinner[] valueBar, int[] resource) {
        this.spinnerKategoriMuncul = valueBar;
        this.resourceKategoriMuncul = resource;
        for (int i = 0; i < jmlSpinner; i++) {
            //mengatur list yang muncul pada halaman BarangMasuk
            List<String> value = Arrays.asList(getResources().getStringArray(resource[i]));
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, value);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            valueBar[i].setAdapter(spinnerAdapter);
            //menghitung harga satuan saat halaman BarangMasuk terbuka
            kalkulatorBarang.setHargaSatuan(valueBar[i].getSelectedItem().toString(), pesananMasuk);
            spinnerAdapter.notifyDataSetChanged();
            //set listener untuk kalkulator
            final int finalI = i;
            valueBar[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    //mendapatkan nilai dari spinner
                    valueBar[finalI].getSelectedItem().toString();
                    //memasukan spinner ke KalkulatorBarang
                    kalkulatorBarang.setHargaSatuan(valueBar[finalI].getSelectedItem().toString(), pesananMasuk);
                    Log.d("ISI SPINNER", valueBar[finalI].getSelectedItem().toString());
                    //mendapatkan harga satuan dari spinner
                    txtHargaSatuan.setText(String.valueOf(kalkulatorBarang.getHargaBarangSatuan()));
                    Log.d("isi total harga satuan", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                    txtHargaTotal.setText(String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                    Log.d("isi total harga total", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    Log.d("isi NOTHING", "NOTHING SELECTED");
                }
            });
        }
    }

    //coba simpan pesanan
    private void simpanPesanan(String[] dataBarang) {
        this.spesifikasiBarang = dataBarang;
        List<String> barang = Arrays.asList(dataBarang);
        Log.d("isi Pesanan", barang.toString());
    }

    //method untuk mendefinisikan data bar kategori
    private void setShowBar() {
        //    Set Identitas setiap bar kategori
        barJmlHalaman = findViewById(R.id.bar_jml_halaman);
        barPilihWarna = findViewById(R.id.bar_pilih_warna);
        barJenisKertas = findViewById(R.id.bar_jenis_kertas);
        barUkuranKertas = findViewById(R.id.bar_ukuran_kertas);
        barSisi = findViewById(R.id.bar_sisi);
        barOrientasi = findViewById(R.id.bar_orientasi);
        barPilihanJilid = findViewById(R.id.bar_pilihan_jilid);
        barJmlCetak = findViewById(R.id.bar_jumlah_cetak);
        barWaktuProses = findViewById(R.id.bar_waktu_proses);
        barPosisiJilid = findViewById(R.id.bar_posisi_jilid);
        barJenisLaminasi = findViewById(R.id.bar_jenis_laminasi);
        barCoverDepan = findViewById(R.id.bar_cover_depan);
        barDuaTiga = findViewById(R.id.bar_foto_2x3);
        barTigaEmpat = findViewById(R.id.bar_foto_3x4);
        barEmpatEnam = findViewById(R.id.bar_foto_4x6);
        barFrame = findViewById(R.id.bar_frame);
        barPanjang = findViewById(R.id.bar_panjang);
        barLebar = findViewById(R.id.bar_lebar);
        barJenisBahan = findViewById(R.id.bar_jenis_bahan);
        barFinishing = findViewById(R.id.bar_finishing);
        barWarnaCetak = findViewById(R.id.bar_warna_cetak);
        barLipatan = findViewById(R.id.bar_lipatan);
        barBajuXs = findViewById(R.id.bar_baju_xs);
        barBajuS = findViewById(R.id.bar_baju_s);
        barBajuM = findViewById(R.id.bar_baju_m);
        barbajuXl = findViewById(R.id.bar_baju_xl);
        barBajuXxl = findViewById(R.id.bar_baju_xxl);
        barJenisCetak = findViewById(R.id.bar_jenis_cetak);
        barJumlah = findViewById(R.id.bar_jml);
        barWarna = findViewById(R.id.bar_warna);
        barUkuranBaju = findViewById(R.id.bar_ukuran_baju);
        barWarnaBahan = findViewById(R.id.bar_warna_bahan);
        barUkuranBahan = findViewById(R.id.bar_ukuran_bahan);
        barBahan = findViewById(R.id.bar_bahan);
        barJmlHalamanSpinner = findViewById(R.id.bar_jml_halaman_spinner);
        barCoverBelakang = findViewById(R.id.bar_cover_belakang);
        barDetilWarna = findViewById(R.id.bar_detil_warna);
        barWarnaFrame = findViewById(R.id.bar_warna_frame);
    }

    //method untuk mendefinisikan value dari setiap kategori
    private void setValueKategori() {
        //    Set identitas value setiap kategori
        edtjmlHalaman = findViewById(R.id.value_jml_halaman);
        edtjmlCetak = findViewById(R.id.value_jml_cetak);
        edtPanjang = findViewById(R.id.value_panjang);
        edtLebar = findViewById(R.id.value_lebar);
        edtJumlah = findViewById(R.id.value_jml);
        edtBajuXs = findViewById(R.id.value_baju_xs);
        edtBajuS = findViewById(R.id.value_baju_s);
        edtBajuM = findViewById(R.id.value_baju_m);
        edtBajuXl = findViewById(R.id.value_baju_xl);
        edtBajuXxl = findViewById(R.id.value_baju_xxl);
        spinPilihWarna = findViewById(R.id.value_pilih_warna);
        spinJenisKertas = findViewById(R.id.value_jenis_kertas);
        spinUkuranKertas = findViewById(R.id.value_ukuran_kertas);
        spinSisi = findViewById(R.id.value_sisi);
        spinOrientasi = findViewById(R.id.value_orientasi);
        spinPilihanJilid = findViewById(R.id.value_pilihan_jilid);
        spinWaktuProses = findViewById(R.id.value_waktu_proses);
        spinPosisiJilid = findViewById(R.id.value_posisi_jilid);
        spinJenisLaminasi = findViewById(R.id.value_jenis_laminasi);
        spinCoverDepan = findViewById(R.id.value_cover_depan);
        spinFotoDuaTiga = findViewById(R.id.value_foto_2x3);
        spinFotoTigaEmpat = findViewById(R.id.value_foto_3x4);
        spinFotoEmpatEnam = findViewById(R.id.value_foto_4x6);
        spinFrame = findViewById(R.id.value_frame);
        spinJenisBahan = findViewById(R.id.value_jenis_bahan);
        spinFinishing = findViewById(R.id.value_finishing);
        spinWarnaCetak = findViewById(R.id.value_warna_cetak);
        spinLipatan = findViewById(R.id.value_lipatan);
        spinJenisCetak = findViewById(R.id.value_jenis_cetak);
        spinWarna = findViewById(R.id.value_warna);
        spinUkuranBaju = findViewById(R.id.value_ukuran_baju);
        spinWarnaBahan = findViewById(R.id.value_warna_bahan);
        spinUkuranBahan = findViewById(R.id.value_ukuran_bahan);
        spinBahan = findViewById(R.id.value_bahan);
        spinJmlHalaman = findViewById(R.id.value_jml_halaman_spinner);
        spinCoverBelakang = findViewById(R.id.value_cover_belakang);
        spinDetilWarna = findViewById(R.id.value_detil_warna);
        spinWarnaFrame = findViewById(R.id.value_warna_frame);

    }

    private void setKomponenHeader() {
        txtJenisLaminasi = findViewById(R.id.txtJenisLaminasi);
        txtCoverDepan = findViewById(R.id.txtCoverDepan);
        txtJenisBahan = findViewById(R.id.txtJenisBahan);
        txtFormat = findViewById(R.id.value_ketentuan_format);
        txtHargaSatuan = findViewById(R.id.harga_satuan);
        txtHargaTotal = findViewById(R.id.harga_total);
    }

    private void changeSubToTitle(TextView textView) {
        textView.setTextSize(20);
        textView.setTextColor(getResources().getColor(R.color.hitam_chatak));
    }

    //method untuk memunculkan bar dan value setiap kategori barang
    private void munculKomponenBarang() {
//menangkap header barang untuk menghitung total harga barang di Kalkulator
        switch (pesananMasuk) {
//    -----------KATEGORI DOKUMEN------------
//      header = Laporan
            case "Laporan":
                //show bar pada laporan
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barPilihWarna, barUkuranKertas, barJenisKertas,
                        barSisi, barOrientasi, barPilihanJilid, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinPilihWarna, spinUkuranKertas, spinJenisKertas, spinSisi,
                        spinOrientasi, spinPilihanJilid};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.pilih_warna, R.array.ukuran_kertas, R.array.jenis_kertas,
                        R.array.sisi, R.array.orientasi, R.array.pilihan_jilid};
                //create kategori
                createBar(9, barKategoriMuncul);
                changeCount("4", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(6, spinnerKategoriMuncul, resourceKategoriMuncul);
                //pengkondisian pilihan jilid
                munculSubKomponenBarang();
                keyUpload = "document/*";
                break;
//     header = Buku
            case "Buku":
                //show bar pada laporan buku
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barPilihWarna, barUkuranKertas, barJenisKertas,
                        barSisi, barOrientasi, barPilihanJilid, barPosisiJilid, barJenisLaminasi,
                        barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinPilihWarna, spinUkuranKertas, spinJenisKertas, spinSisi,
                        spinOrientasi, spinPilihanJilid, spinPosisiJilid, spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.pilih_warna, R.array.ukuran_kertas, R.array.jenis_kertas,
                        R.array.sisi2, R.array.orientasi, R.array.pilihan_jilid_2, R.array.posisi_jilid,
                        R.array.jenis_laminasi};
                //create kategori
                createBar(11, barKategoriMuncul);
                changeCount("4", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(8, spinnerKategoriMuncul, resourceKategoriMuncul);
                keyUpload = "document/*";
                break;
//     header = Berkas
            //show bar pada berkas
            case "Berkas":
                //show bar Berkas
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barPilihWarna, barUkuranKertas, barJenisKertas, barSisi,
                        barOrientasi, barPilihanJilid, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinPilihWarna, spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi,
                        spinPilihanJilid};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.pilih_warna, R.array.ukuran_kertas, R.array.jenis_kertas, R.array.sisi,
                        R.array.orientasi, R.array.pilihan_jilid_3};
                //create kategori
                createBar(9, barKategoriMuncul);
                changeCount("1", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(6, spinnerKategoriMuncul, resourceKategoriMuncul);
                munculSubKomponenBarang();
                keyUpload = "document/*";
                break;
//    header = Sertifikat
            case "Sertifikat":
                //show bar Sertifikat
                barKategoriMuncul = new LinearLayout[]{barUkuranKertas, barJenisKertas, barSisi, barOrientasi,
                        barJenisLaminasi, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi,
                        spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas, R.array.jenis_kertas_2, R.array.sisi, R.array.orientasi,
                        R.array.jenis_laminasi_2};
                //create kategori
                createBar(7, barKategoriMuncul);
                createValue(5, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                changeCount("2", edtjmlCetak);
                keyUpload = "document/*";
                break;
//    header = Portofolio
            case "Portofolio":
                //show bar Portofolio
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi, barOrientasi,
                        barPilihanJilid, barPosisiJilid, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi, spinPilihanJilid,
                        spinPosisiJilid};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_2, R.array.jenis_kertas_3, R.array.sisi1, R.array.orientasi,
                        R.array.pilihan_jilid_4, R.array.posisi_jilid_2};
                //create kategori
                createBar(9, barKategoriMuncul);
                changeCount("4", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(6, spinnerKategoriMuncul, resourceKategoriMuncul);
                keyUpload = "document/*";
                break;
//    header = Skripsi
            case "Skripsi":
                //show bar Skripsi
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barPilihWarna, barUkuranKertas, barJenisKertas, barSisi,
                        barOrientasi, barPilihanJilid, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinPilihWarna, spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi,
                        spinPilihanJilid};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.pilih_warna, R.array.ukuran_kertas, R.array.jenis_kertas, R.array.sisi,
                        R.array.orientasi, R.array.pilihan_jilid_5};
                //create kategori
                createBar(9, barKategoriMuncul);
                changeCount("50", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(6, spinnerKategoriMuncul, resourceKategoriMuncul);
                keyUpload = "document/*";
                break;
//    header = Buku Lipat
            case "Buku Lipat":
                //show bar Buku Lipat
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barPilihWarna, barUkuranKertas, barJenisKertas, barSisi,
                        barOrientasi, barPilihanJilid, barPosisiJilid, barCoverDepan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinPilihWarna, spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi,
                        spinPilihanJilid, spinPosisiJilid, spinCoverDepan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.pilih_warna, R.array.ukuran_kertas, R.array.jenis_kertas, R.array.sisi2,
                        R.array.orientasi_portrait, R.array.pilihan_jilid_6, R.array.posisi_jilid, R.array.cover_depan};
                //create kategori
                createBar(11, barKategoriMuncul);
                changeCount("6", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(8, spinnerKategoriMuncul, resourceKategoriMuncul);
                keyUpload = "document/*";
                break;
//    header =  Notebook
            case "Notebook":
                //show bar Notebook
                barKategoriMuncul = new LinearLayout[]{barJmlHalamanSpinner, barUkuranKertas, barJenisKertas, barPilihanJilid, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJmlHalaman, spinUkuranKertas, spinJenisKertas, spinPilihanJilid};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jml_halaman_1, R.array.ukuran_kertas_3, R.array.jenis_kertas_4, R.array.pilihan_jilid_7};
                //create kategori
                createBar(5, barKategoriMuncul);
                createValue(4, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Nota
            case "Nota":
                //show bar Nota
                barKategoriMuncul = new LinearLayout[]{barJmlHalamanSpinner, barPilihWarna, barUkuranKertas, barJenisKertas, barOrientasi, barPilihanJilid,
                        barCoverDepan, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJmlHalaman, spinPilihWarna, spinUkuranKertas, spinJenisKertas, spinOrientasi,
                        spinPilihanJilid, spinCoverDepan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jml_halaman_2, R.array.pilih_warna_2, R.array.ukuran_kertas_4, R.array.jenis_kertas_5, R.array.orientasi,
                        R.array.pilihan_jilid_8, R.array.cover_depan_2};
                //create kategori
                createBar(8, barKategoriMuncul);
                createValue(7, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png_pdf);
                keyUpload = "document/*";
                break;
//    header = Kop Surat
            case "Kop Surat":
                //show bar Kop Surat
                barKategoriMuncul = new LinearLayout[]{barJmlHalamanSpinner, barPilihWarna, barUkuranKertas,
                        barJenisKertas, barCoverDepan, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJmlHalaman, spinPilihWarna, spinUkuranKertas, spinJenisKertas,
                        spinCoverDepan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jml_halaman_3, R.array.pilih_warna_3, R.array.ukuran_kertas_5,
                        R.array.jenis_kertas_6, R.array.cover_depan_3};
                //create kategori
                createBar(6, barKategoriMuncul);
                createValue(5, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtCoverDepan);
                txtFormat.setText(R.string.format_jpg_png_pdf);
                keyUpload = "document/*";
                break;
//    header = Map
            case "Map":
                //show bar Map
                barKategoriMuncul = new LinearLayout[]{barPilihWarna, barUkuranKertas, barJenisKertas, barSisi,
                        barJenisLaminasi, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinPilihWarna, spinUkuranKertas, spinJenisKertas, spinSisi,
                        spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.pilih_warna_4, R.array.ukuran_kertas_6, R.array.jenis_kertas_9,
                        R.array.sisi, R.array.jenis_laminasi_3};
                //create kategori
                createBar(6, barKategoriMuncul);
                createValue(5, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;

//    header = Amplop
            case "Amplop":
                //show bar Amplop
                barKategoriMuncul = new LinearLayout[]{barJmlHalamanSpinner, barPilihWarna, barJenisKertas, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJmlHalaman, spinPilihWarna, spinJenisKertas};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jml_halaman_4, R.array.pilih_warna_3, R.array.jenis_kertas_7};
                //create kategori
                changeCount("2", edtjmlCetak);
                createBar(5, barKategoriMuncul);
                createValue(3, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png_pdf);
                keyUpload = "document/*";
                break;
//    header = Pas Foto
            case "Pas Foto":
                //show bar Pas Foto
                barKategoriMuncul = new LinearLayout[]{barDuaTiga, barTigaEmpat, barEmpatEnam, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinFotoDuaTiga, spinFotoTigaEmpat, spinFotoEmpatEnam};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jumlah_foto, R.array.jumlah_foto, R.array.jumlah_foto};
                //create kategori
                createBar(4, barKategoriMuncul);
                createValue(3, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png_pdf);
                keyUpload = "document/*";
                break;
//    header = Foto
            case "Foto":
                //show bar Foto
                barKategoriMuncul = new LinearLayout[]{barJenisKertas, barUkuranKertas, barFrame, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisKertas, spinUkuranKertas, spinFrame};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_kertas_8, R.array.ukuran_kertas_7, R.array.frame};
                //create kategori
                createBar(4, barKategoriMuncul);
                createValue(3, spinnerKategoriMuncul, resourceKategoriMuncul);
                munculSubKomponenBarang();
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;

//    -----------KATEGORI MEDIA PROMOSI------------

//    header = Name Tag
            case "Name Tag":
                //show bar Name Tag
                barKategoriMuncul = new LinearLayout[]{barUkuranKertas, barJenisKertas, barSisi, barOrientasi,
                        barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_8, R.array.jenis_kertas_10, R.array.sisi, R.array.orientasi};
                //create kategori
                changeCount("2", edtjmlCetak);
                createBar(6, barKategoriMuncul);
                createValue(4, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_pdf_jpg);
                keyUpload = "document/*";
                break;
//    header = Kartu Nama
            case "Kartu Nama":
                //show bar Kartu Nama
                barKategoriMuncul = new LinearLayout[]{barUkuranKertas, barJenisKertas, barSisi, barJenisLaminasi,
                        barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_9, R.array.jenis_kertas_11, R.array.sisi, R.array.jenis_laminasi_3};
                //create kategori
                changeCount("2", edtjmlCetak);
                createBar(6, barKategoriMuncul);
                createValue(4, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                txtFormat.setText(R.string.format_pdf_jpg);
                keyUpload = "document/*";
                break;
//    header = Spanduk
            case "Spanduk":
                //show bar Spanduk
                barKategoriMuncul = new LinearLayout[]{barPanjang, barLebar, barJenisBahan,
                        barFinishing, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisBahan, spinFinishing};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_bahan, R.array.finishing_2};
                //create kategori
                createBar(6, barKategoriMuncul);
                changeCount("2", edtPanjang);
                changeCount("2", edtLebar);
                changeCount("2", edtjmlCetak);
                createValue(2, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisBahan);
                keyUpload = "document/*";
                break;
//    header = Poster
            case "Poster":
                //show bar Poster
                barKategoriMuncul = new LinearLayout[]{barUkuranKertas, barJenisKertas, barSisi, barJenisLaminasi,
                        barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_10, R.array.jenis_kertas_12, R.array.sisi1, R.array.jenis_laminasi_3};
                //create kategori
                createBar(6, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                createValue(4, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                keyUpload = "document/*";
                break;
//     header = Stand Banner
            case "Stand Banner":
                //show bar Stand Banner
                barKategoriMuncul = new LinearLayout[]{barUkuranBahan, barJenisBahan, barSisi, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranBahan, spinJenisBahan, spinSisi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_bahan_2, R.array.jenis_bahan_2, R.array.sisi1};
                //create kategori
                createBar(5, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                createValue(3, spinnerKategoriMuncul, resourceKategoriMuncul);
                munculSubKomponenBarang();
                keyUpload = "document/*";
                break;
//     header = Flyer Ekonomis
            case "Flyer Ekonomis":
                //show bar Sertifikat
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi, barWarnaCetak,
                        barLipatan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinWarnaCetak,
                        spinLipatan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas, R.array.jenis_kertas, R.array.sisi, R.array.warna_cetak,
                        R.array.lipatan};
                //create kategori
                createBar(8, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                changeCount("4", edtjmlHalaman);
                createValue(5, spinnerKategoriMuncul, resourceKategoriMuncul);
                keyUpload = "document/*";
                break;
//     header = brosur ekonomis
            case "Brosur Ekonomis":
                //show bar Brosur Ekonomis
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi, barWarnaCetak,
                        barLipatan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinWarnaCetak, spinLipatan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas, R.array.jenis_kertas, R.array.sisi, R.array.warna_cetak, R.array.lipatan};
                //create kategori
                createBar(8, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                changeCount("4", edtjmlHalaman);
                createValue(5, spinnerKategoriMuncul, resourceKategoriMuncul);
                keyUpload = "document/*";
                break;
//     header = Baliho
            case "Baliho":
                //show bar Baliho
                barKategoriMuncul = new LinearLayout[]{barPanjang, barLebar, barJenisBahan,
                        barFinishing, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisBahan, spinFinishing};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_bahan, R.array.finishing_2};
                //create kategori
                createBar(6, barKategoriMuncul);
                changeCount("1", edtPanjang);
                changeCount("1", edtLebar);
                changeCount("2", edtjmlCetak);
                createValue(2, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisBahan);
                keyUpload = "document/*";
                break;
//     header = Booklet
            case "Booklet":
                //show bar Booklet
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi,
                        barOrientasi, barPilihanJilid, barPosisiJilid, barCoverDepan,
                        barJenisLaminasi, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi,
                        spinPilihanJilid, spinPosisiJilid, spinCoverDepan, spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_11, R.array.jenis_kertas_12, R.array.sisi2, R.array.orientasi_portrait,
                        R.array.pilihan_jilid_6, R.array.posisi_jilid, R.array.cover_depan, R.array.jenis_laminasi_3};
                //create kategori
                createBar(11, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                changeCount("8", edtjmlHalaman);
                createValue(8, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                keyUpload = "document/*";
                break;
//     header = Katalog
            case "Katalog":
                //show bar Katalog
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi, barOrientasi,
                        barPilihanJilid, barPosisiJilid, barCoverDepan, barJenisLaminasi,
                        barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi, spinPilihanJilid,
                        spinPosisiJilid, spinCoverDepan, spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_11, R.array.jenis_kertas_13, R.array.sisi2, R.array.orientasi,
                        R.array.pilihan_jilid_4, R.array.posisi_jilid_2, R.array.cover_depan, R.array.jenis_laminasi_3};
                //create kategori
                createBar(11, barKategoriMuncul);
                changeCount("6", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(8, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                keyUpload = "document/*";
                break;
//     header = Kain
            case "Kain":
                //show bar Kain
                barKategoriMuncul = new LinearLayout[]{barPanjang, barLebar, barJenisBahan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_bahan_3};
                //create kategori
                createBar(5, barKategoriMuncul);
                changeCount("1", edtPanjang);
                changeCount("1", edtLebar);
                changeCount("2", edtjmlCetak);
                createValue(1, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisBahan);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//     header = Bendera
            case "Bendera":
                //show bar Bendera
                barKategoriMuncul = new LinearLayout[]{barPanjang, barLebar, barJenisBahan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_bahan_3};
                //create kategori
                createBar(5, barKategoriMuncul);
                changeCount("1", edtPanjang);
                changeCount("1", edtLebar);
                changeCount("2", edtjmlCetak);
                createValue(1, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisBahan);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//     header = Poster Hitam Putih
            case "Poster Hitam Putih":
                //show bar Poster Hitam Putih
                barKategoriMuncul = new LinearLayout[]{barPilihWarna, barUkuranKertas, barJenisKertas, barSisi,
                        barOrientasi, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinPilihWarna, spinUkuranKertas, spinJenisKertas, spinSisi,
                        spinOrientasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.pilih_warna_5, R.array.ukuran_kertas_10, R.array.jenis_kertas_14,
                        R.array.sisi1, R.array.orientasi_portrait};
                //create kategori
                changeCount("1", edtjmlCetak);
                createBar(7, barKategoriMuncul);
                createValue(5, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//     header = Poster Standard
            case "Poster Standard":
                //show bar Poster Standar
                barKategoriMuncul = new LinearLayout[]{barUkuranKertas, barJenisKertas, barSisi, barOrientasi,
                        barJenisLaminasi, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinOrientasi,
                        spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_12, R.array.jenis_kertas_15, R.array.sisi1,
                        R.array.orientasi_portrait, R.array.jenis_laminasi_3};
                //create kategori
                createBar(7, barKategoriMuncul);
                changeCount("1", edtjmlCetak);
                createValue(5, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Brosur Hitam Putih
            case "Brosur Hitam Putih":
                //show bar Brosur Hitam Putih
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi, barWarnaCetak,
                        barOrientasi, barLipatan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinWarnaCetak, spinOrientasi,
                        spinLipatan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_13, R.array.jenis_kertas_14, R.array.sisi, R.array.warna_cetak,
                        R.array.orientasi, R.array.lipatan_2};
                //create kategori
                createBar(9, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                changeCount("4", edtjmlHalaman);
                createValue(6, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header =  Flyer Hitam Putih
            case "Flyer Hitam Putih":
                //show bar Flyer Hitam Putih
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi, barWarnaCetak,
                        barOrientasi, barLipatan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinWarnaCetak, spinOrientasi,
                        spinLipatan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_13, R.array.jenis_kertas_14, R.array.sisi, R.array.warna_cetak,
                        R.array.orientasi, R.array.lipatan_2};
                //create kategori
                createBar(9, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                changeCount("4", edtjmlHalaman);
                createValue(6, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header =  Brosur
            case "Brosur":
                //show bar Brosur
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi, barWarnaCetak,
                        barOrientasi, barJenisLaminasi, barLipatan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinWarnaCetak, spinOrientasi,
                        spinJenisLaminasi, spinLipatan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_14, R.array.jenis_kertas_16, R.array.sisi, R.array.warna_cetak,
                        R.array.orientasi, R.array.jenis_laminasi_3, R.array.lipatan_2};
                //create kategori
                createBar(10, barKategoriMuncul);
                changeCount("4", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(7, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Flyer Standar
            case "Flyer Standard":
                //show bar Flyer Standar
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barSisi, barWarnaCetak,
                        barOrientasi, barJenisLaminasi, barLipatan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinSisi, spinWarnaCetak, spinOrientasi,
                        spinJenisLaminasi, spinLipatan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_14, R.array.jenis_kertas_16, R.array.sisi, R.array.warna_cetak,
                        R.array.orientasi, R.array.jenis_laminasi_3, R.array.lipatan_2};
                //create kategori
                createBar(10, barKategoriMuncul);
                changeCount("4", edtjmlHalaman);
                changeCount("2", edtjmlCetak);
                createValue(7, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Stempel
            case "Stempel":
                //show bar Stempel
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barWarnaCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinWarnaCetak};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_15, R.array.jenis_kertas_17, R.array.warna_cetak_2};
                //create kategori
                createBar(5, barKategoriMuncul);
                changeCount("4", edtjmlHalaman);
                createValue(3, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Cutting Sticker
            case "Cutting Sticker":
                //show bar Cutting Sticker
                barKategoriMuncul = new LinearLayout[]{barJmlHalaman, barUkuranKertas, barJenisKertas, barWarnaCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas, spinWarnaCetak};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_15, R.array.jenis_kertas_17, R.array.warna_cetak_2};
                //create kategori
                createBar(5, barKategoriMuncul);
                changeCount("4", edtjmlHalaman);
                createValue(3, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Sticker
            case "Sticker":
                //show bar Sticker
                barKategoriMuncul = new LinearLayout[]{barPanjang, barLebar, barJenisKertas, barJenisLaminasi, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisKertas, spinJenisLaminasi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_kertas_18, R.array.jenis_laminasi_3};
                //create kategori
                createBar(5, barKategoriMuncul);
                changeCount("1", edtPanjang);
                changeCount("1", edtLebar);
                createValue(2, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                keyUpload = "document/*";
                break;
//   header = ID Card
            case "ID Card":
                //show bar ID Card
                barKategoriMuncul = new LinearLayout[]{};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{};
                //set value spinner
                resourceKategoriMuncul = new int[]{};
                //create kategori
                createBar(0, barKategoriMuncul);
                changeCount("0", edtjmlHalaman);
                createValue(0, spinnerKategoriMuncul, resourceKategoriMuncul);
                break;
//   header =  Tali ID Card
            case "Tali ID Card":
                //show bar Tali ID Card
                barKategoriMuncul = new LinearLayout[]{};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{};
                //set value spinner
                resourceKategoriMuncul = new int[]{};
                //create kategori
                createBar(0, barKategoriMuncul);
                changeCount("0", edtjmlHalaman);
                createValue(0, spinnerKategoriMuncul, resourceKategoriMuncul);
                break;
//   header = Topi
            case "Topi":
                //show bar Topi
                barKategoriMuncul = new LinearLayout[]{barUkuranKertas, barJenisKertas, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_18, R.array.jenis_kertas_20};
                //create kategori
                createBar(4, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                createValue(2, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//   header = Kaos Satuan
            case "Kaos Satuan":
                //show bar Kaos Satuan
                barKategoriMuncul = new LinearLayout[]{barWarna, barJenisBahan, barJenisLaminasi, barBajuXs,
                        barBajuS, barBajuM, barbajuXl, barBajuXxl, barJenisCetak,
                        barJumlah, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinWarna, spinJenisBahan, spinJenisLaminasi, spinJenisCetak};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.warna, R.array.jenis_bahan_4, R.array.jenis_laminasi_4, R.array.jenis_cetak_2};
                //create kategori
                createBar(11, barKategoriMuncul);
                changeCount("1", edtBajuXs);
                changeCount("1", edtBajuS);
                changeCount("1", edtBajuM);
                changeCount("1", edtBajuXl);
                changeCount("1", edtBajuXxl);
                changeCount("2", edtJumlah);
                createValue(4, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                changeSubToTitle(txtJenisBahan);
                txtFormat.setText(R.string.format_jpg_png);
                munculSubKomponenBarang();
                keyUpload = "image/*";
                break;
//   header = Kaos Standar
            case "Kaos Standard":
                //show bar Kaos Standar
                barKategoriMuncul = new LinearLayout[]{barWarna, barJenisBahan, barJenisLaminasi, barUkuranBaju,
                        barJenisCetak, barJumlah, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinWarna, spinJenisBahan, spinJenisLaminasi, spinJenisCetak};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.warna, R.array.jenis_bahan_4, R.array.jenis_laminasi_4, R.array.jenis_cetak};
                //create kategori
                createBar(7, barKategoriMuncul);
                changeCount("2", edtJumlah);
                createValue(4, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                changeSubToTitle(txtJenisBahan);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Kaos Ekonomis
            case "Kaos Ekonomis":
                //show bar Kaos Ekonomis
                barKategoriMuncul = new LinearLayout[]{barWarna, barJenisBahan, barJenisLaminasi, barUkuranBaju,
                        barJenisCetak, barJumlah, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinWarna, spinJenisBahan, spinJenisLaminasi, spinJenisCetak};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.warna, R.array.jenis_bahan_4, R.array.jenis_laminasi_4,
                        R.array.jenis_cetak_3};
                //create kategori
                createBar(7, barKategoriMuncul);
                changeCount("2", edtJumlah);
                createValue(4, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisLaminasi);
                changeSubToTitle(txtJenisBahan);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    -----------KATEGORI SOUVENIR------------

//    header = Mug
            case "Mug":
                //show bar Mug
                barKategoriMuncul = new LinearLayout[]{barJenisBahan, barWarnaBahan, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisBahan, spinWarnaBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_bahan_5, R.array.warna_bahan};
                //create kategori
                createBar(4, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                createValue(2, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisBahan);
                spinJenisBahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        //get data ukuran bahan
                        spinJenisBahan.getSelectedItem().toString();
                        //menghitung harga mug
                        kalkulatorBarang.setHargaSatuan(spinJenisBahan.getSelectedItem().toString(), pesananMasuk);
                        //memanggil kembali harga karena data tidak bisa diambil dari listenCount
                        txtHargaSatuan.setText(String.valueOf(kalkulatorBarang.getHargaBarangSatuan()));
                        Log.d("isi total harga satuan", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                        txtHargaTotal.setText(String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                        Log.d("isi total harga total", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                        Log.d("isi JENIS BAHAN", spinJenisBahan.getSelectedItem().toString());
                        //set kondisi ukuran bahan
                        //pengkondisian mulai
                        if (spinJenisBahan.getSelectedItem().toString().equalsIgnoreCase("Mug Transparan") ||
                                spinJenisBahan.getSelectedItem().toString().equalsIgnoreCase("Mug Bunglon") ||
                                spinJenisBahan.getSelectedItem().toString().equalsIgnoreCase("Mug Kembung")) {
                            barWarnaBahan.setVisibility(View.GONE);
                            jmlData = "3";
                            Log.d("isi Barang", jmlData);
                        } else {
                            barKategoriMuncul = new LinearLayout[]{barWarnaBahan};
                            barKategoriMuncul[0].setVisibility(muncul);
                            jmlData = "4";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        Log.d("isi NOTHING", "NOTHING SELECTED");
                    }
                });
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Bolpoin
            case "Bolpoin":
                //show bar Bolpoin
                barKategoriMuncul = new LinearLayout[]{barUkuranBahan, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_bahan_3};
                //create kategori
                createBar(2, barKategoriMuncul);
                createValue(1, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Pin
            case "Pin":
                //show bar Pin
                barKategoriMuncul = new LinearLayout[]{barJenisBahan, barBahan, barFinishing, barJumlah, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisBahan, spinBahan, spinFinishing};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_bahan_6, R.array.bahan, R.array.finishing};
                //create kategori
                createBar(5, barKategoriMuncul);
                changeCount("2", edtJumlah);
                createValue(3, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisBahan);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//    header = Gantungan kunci
            case "Gantungan kunci":
                //show bar Gantungan Kunci
                barKategoriMuncul = new LinearLayout[]{barJenisBahan, barBahan, barSisi, barJumlah, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisBahan, spinBahan, spinSisi};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_bahan_7, R.array.bahan, R.array.sisi};
                //create kategori
                createBar(5, barKategoriMuncul);
                changeCount("2", edtJumlah);
                createValue(3, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisBahan);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//   header = Tumbler
            case "Tumbler":
                //show bar Tumbler
                barKategoriMuncul = new LinearLayout[]{barUkuranBahan, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_bahan_4};
                //create kategori
                createBar(2, barKategoriMuncul);
                createValue(1, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//   header = Gantungan kunci Akrilik
            case "Gantungan kunci Akrilik":
                //show bar Gantungan Akrilik
                barKategoriMuncul = new LinearLayout[]{barUkuranBahan, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_bahan_5};
                //create kategori
                createBar(2, barKategoriMuncul);
                createValue(1, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//   header = Kalender Dinding
            case "Kalender Dinding":
                //show bar Kalender Dinding
                barKategoriMuncul = new LinearLayout[]{barUkuranKertas, barJenisKertas, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_16, R.array.jenis_kertas_19};
                //create kategori
                createBar(3, barKategoriMuncul);
                createValue(2, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//   header Kalender Meja
            case "Kalender Meja":
                //show bar Kalender Meja
                barKategoriMuncul = new LinearLayout[]{barUkuranKertas, barJenisKertas, barJmlCetak, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranKertas, spinJenisKertas};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_kertas_17, R.array.jenis_kertas_9};
                //create kategori
                createBar(4, barKategoriMuncul);
                changeCount("2", edtjmlCetak);
                createValue(2, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//   header = Payung
            case "Payung":
                //show bar Payung
                barKategoriMuncul = new LinearLayout[]{barJenisBahan, barFinishing, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinJenisBahan, spinFinishing};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.jenis_bahan_8, R.array.finishing_3};
                //create kategori
                createBar(3, barKategoriMuncul);
                createValue(2, spinnerKategoriMuncul, resourceKategoriMuncul);
                changeSubToTitle(txtJenisBahan);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//  header = Jam Dinding
            case "Jam Dinding":
                //show bar Kalender Meja
                barKategoriMuncul = new LinearLayout[]{barUkuranBahan, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_bahan};
                //create kategori
                createBar(2, barKategoriMuncul);
                createValue(1, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//  header = Tas Kresek
            case "Tas Kresek":
                //show bar Tas Kresek
                barKategoriMuncul = new LinearLayout[]{barUkuranBahan, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_bahan_6};
                //create kategori
                createBar(2, barKategoriMuncul);
                createValue(1, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(R.string.format_jpg_png);
                keyUpload = "image/*";
                break;
//  header = Kipas
            case "Kipas":
                //show bar Kipas
                barKategoriMuncul = new LinearLayout[]{barUkuranBahan, barWaktuProses};
                //set spinner
                spinnerKategoriMuncul = new Spinner[]{spinUkuranBahan};
                //set value spinner
                resourceKategoriMuncul = new int[]{R.array.ukuran_bahan_7};
                //create kategori
                createBar(2, barKategoriMuncul);
                createValue(1, spinnerKategoriMuncul, resourceKategoriMuncul);
                txtFormat.setText(getString(R.string.format_jpg_png));
                keyUpload = "image/*";
                break;
        }
//      ----------END KATEGORI-----------
    }

    //  method untuk get data jumlah setiap perubahan input
    private void listenCount(final EditText edt) {
        edt.getText();
        //mendapatkan id edit text agar perhitungan bisa di proses
        final double jumlahBarang = Double.parseDouble(String.valueOf(edt.getText()));
        kalkulatorBarang.hitungJumlahBarang(jumlahBarang, edt, pesananMasuk);
        //memasukan data edittext ke BarangModel
        Log.d("isi EDITTEXT", String.valueOf(jumlahBarang));
        Log.d("isi ID", edt.toString());
        //listen jumlah untuk kalkulator
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String.valueOf(edt.getText());
                Log.d("isi data ganti", String.valueOf(edt.getText()));
                try {
                    double jumlahBarangGanti = Double.parseDouble(String.valueOf(edt.getText()));
                    Log.d("isi data ganti", String.valueOf(edt.getText()));
                    kalkulatorBarang.hitungJumlahBarang(jumlahBarangGanti, edt, pesananMasuk);
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String.valueOf(edt.getText());
                Log.d("isi data before", String.valueOf(edt.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                String.valueOf(edt.getText());
                Log.d("isi data after", String.valueOf(edt.getText()));
                txtHargaSatuan.setText(String.valueOf(kalkulatorBarang.getHargaBarangSatuan()));
                Log.d("isi total harga satuan", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                txtHargaTotal.setText(String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                Log.d("isi total harga total", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
            }
        });

    }

    private void munculSubKomponenBarang() {
        spinFrame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinFrame.getSelectedItem().toString();
                barKategoriMuncul = new LinearLayout[]{barWarnaFrame};
                if (spinFrame.getSelectedItem().toString().equalsIgnoreCase("Frame Minimalis")) {
                    barKategoriMuncul[0].setVisibility(muncul);
                    spinners = new Spinner[]{spinWarnaFrame};
                    isi = new int[]{R.array.warna_frame};
                    createValue(1, spinners, isi);
                    jmlData = "7";
                    spinWarnaFrame.getSelectedItem().toString();
                    kalkulatorBarang.setHargaSatuan(spinWarnaFrame.getSelectedItem().toString(), pesananMasuk);
                    //memanggil kembali harga karena data tidak bisa diambil dari listenCount
                    txtHargaSatuan.setText(String.valueOf(kalkulatorBarang.getHargaBarangSatuan()));
                    Log.d("isi total harga satuan", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                    txtHargaTotal.setText(String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                    Log.d("isi total harga total", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                } else {
                    barKategoriMuncul[0].setVisibility(View.GONE);
                    jmlData = "6";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinPilihanJilid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //get data pilihan jilid
                spinPilihanJilid.getSelectedItem().toString();
                Log.d("isi JILID", spinPilihanJilid.getSelectedItem().toString());
                //set kondisi pilihan jilid
                barKategoriMuncul = new LinearLayout[]{barPosisiJilid, barCoverDepan, barCoverBelakang};
                //pengkondisian mulai
                if (spinPilihanJilid.getSelectedItem().toString().equalsIgnoreCase("Hekter")) {
                    barKategoriMuncul[0].setVisibility(muncul);
                    barKategoriMuncul[1].setVisibility(View.GONE);
                    barKategoriMuncul[2].setVisibility(View.GONE);
                    spinners = new Spinner[]{spinPosisiJilid};
                    isi = new int[]{R.array.sub_posisi_jilid};
                    createValue(1, spinners, isi);
                    jmlData = "12";
                    Log.d("isi Barang", jmlData);
                } else if (spinPilihanJilid.getSelectedItem().toString().equalsIgnoreCase("Biasa/Lakban") ||
                        spinPilihanJilid.getSelectedItem().toString().equalsIgnoreCase("Ring Kawat") ||
                        spinPilihanJilid.getSelectedItem().toString().equalsIgnoreCase("Ring Plastik")) {
                    for (LinearLayout linearLayout : barKategoriMuncul) {
                        linearLayout.setVisibility(muncul);
                    }
                    spinners = new Spinner[]{spinPosisiJilid, spinCoverDepan, spinCoverBelakang};
                    isi = new int[]{R.array.sub_posisi_jilid, R.array.sub_cover_depan, R.array.sub_cover_belakang};
                    createValue(3, spinners, isi);
                    jmlData = "14";
                    Log.d("isi Barang", jmlData);
                } else {
                    for (LinearLayout linearLayout : barKategoriMuncul) {
                        linearLayout.setVisibility(View.GONE);
                    }
                    jmlData = "9";
                    Log.d("isi Barang", jmlData);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("isi NOTHING", "NOTHING SELECTED");
            }
        });
        spinUkuranBahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //get data ukuran bahan
                spinUkuranBahan.getSelectedItem().toString();
                Log.d("isi UKURAN BAHAN", spinUkuranBahan.getSelectedItem().toString());
                kalkulatorBarang.setHargaSatuan(spinUkuranBahan.getSelectedItem().toString(), pesananMasuk);
                //memanggil kembali harga karena data tidak bisa diambil dari listenCount
                txtHargaSatuan.setText(String.valueOf(kalkulatorBarang.getHargaBarangSatuan()));
                Log.d("isi total harga satuan", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                txtHargaTotal.setText(String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                Log.d("isi total harga total", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                //set kondisi ukuran bahan
                //pengkondisian mulai
                if (spinUkuranBahan.getSelectedItem().toString().equalsIgnoreCase("Y Banner 160x80 Cm") ||
                        spinUkuranBahan.getSelectedItem().toString().equalsIgnoreCase("Roll Up Banner 160x60 Cm") ||
                        spinUkuranBahan.getSelectedItem().toString().equalsIgnoreCase("Roll Up Banner 200x80 Cm")) {
                    barJenisBahan.setVisibility(View.GONE);
                    jmlData = "4";
                    Log.d("isi Barang", jmlData);
                } else {
                    barKategoriMuncul = new LinearLayout[]{barJenisBahan};
                    barKategoriMuncul[0].setVisibility(muncul);
                    jmlData = "5";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("isi NOTHING", "NOTHING SELECTED");
            }
        });
        spinWarna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get data ukuran bahan
                spinWarna.getSelectedItem().toString();
                Log.d("isi UKURAN BAHAN", spinWarna.getSelectedItem().toString());
                kalkulatorBarang.setHargaSatuan(spinWarna.getSelectedItem().toString(), pesananMasuk);
                //memanggil kembali harga karena data tidak bisa diambil dari listenCount
                txtHargaSatuan.setText(String.valueOf(kalkulatorBarang.getHargaBarangSatuan()));
                Log.d("isi total harga satuan", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                txtHargaTotal.setText(String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                Log.d("isi total harga total", String.valueOf(kalkulatorBarang.getTotalHargaBarang()));
                //set kondisi ukuran bahan
                //pengkondisian mulai
                if (spinWarna.getSelectedItem().toString().equalsIgnoreCase("Warna") ||
                        spinWarna.getSelectedItem().toString().equalsIgnoreCase("Raglan")) {
                    barDetilWarna.setVisibility(muncul);
                    spinners = new Spinner[]{spinDetilWarna};
                    isi = new int[]{R.array.detil_warna};
                    createValue(1, spinners, isi);
                    jmlData = "12";
                    Log.d("isi Barang", jmlData);
                } else {
                    barDetilWarna.setVisibility(View.GONE);
                    jmlData = "11";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("isi NOTHING", "NOTHING SELECTED");
            }
        });
    }

    //method untuk menyimpan data pesanan
    private void setValueInput() {
        //set nilai untuk konfirmasi barang
        sJmlHalaman = String.valueOf(edtjmlHalaman.getText());
        sJmlHalamanSpinner = String.valueOf(spinJmlHalaman.getSelectedItem());
        sPilihWarna = String.valueOf(spinPilihWarna.getSelectedItem());
        sJenisKertas = String.valueOf(spinJenisKertas.getSelectedItem());
        sUkuranKertas = String.valueOf(spinUkuranKertas.getSelectedItem());
        sSisi = String.valueOf(spinSisi.getSelectedItem());
        sOrientasi = String.valueOf(spinOrientasi.getSelectedItem());
        sPilihanJilid = String.valueOf(spinPilihanJilid.getSelectedItem());
        sJmlCetak = String.valueOf(edtjmlCetak.getText());
        sWaktuProses = String.valueOf(spinWaktuProses.getSelectedItem());
        sPosisiJilid = String.valueOf(spinPosisiJilid.getSelectedItem());
        sJenisLaminasi = String.valueOf(spinJenisLaminasi.getSelectedItem());
        sCoverDepan = String.valueOf(spinCoverDepan.getSelectedItem());
        sDuaTiga = String.valueOf(spinFotoDuaTiga.getSelectedItem());
        sTigaEmpat = String.valueOf(spinFotoTigaEmpat.getSelectedItem());
        sEmpatEnam = String.valueOf(spinFotoEmpatEnam.getSelectedItem());
        sFrame = String.valueOf(spinFrame.getSelectedItem());
        sPanjang = String.valueOf(edtPanjang.getText());
        sLebar = String.valueOf(edtLebar.getText());
        sJenisBahan = String.valueOf(spinJenisBahan.getSelectedItem());
        sFinishing = String.valueOf(spinFinishing.getSelectedItem());
        sWarnaCetak = String.valueOf(spinWarnaCetak.getSelectedItem());
        sLipatan = String.valueOf(spinLipatan.getSelectedItem());
        sBajuXs = String.valueOf(edtBajuXs.getText());
        sBajuS = String.valueOf(edtBajuS.getText());
        sBajuM = String.valueOf(edtBajuM.getText());
        sbajuXl = String.valueOf(edtBajuXl.getText());
        sBajuXxl = String.valueOf(edtBajuXxl.getText());
        sJenisCetak = String.valueOf(spinJenisCetak.getSelectedItem());
        sJumlah = String.valueOf(edtJumlah.getText());
        sWarna = String.valueOf(spinWarna.getSelectedItem());
        sUkuranBaju = String.valueOf(spinUkuranBaju.getSelectedItem());
        sWarnaBahan = String.valueOf(spinWarnaBahan.getSelectedItem());
        sUkuranBahan = String.valueOf(spinUkuranBahan.getSelectedItem());
        sBahan = String.valueOf(spinBahan.getSelectedItem());
        sCoverBelakang = String.valueOf(spinCoverBelakang.getSelectedItem());
        sDetilWarna = String.valueOf(spinDetilWarna.getSelectedItem());
        sHargaSatuan = String.valueOf(txtHargaSatuan.getText());
        sHargaTotal = String.valueOf(txtHargaTotal.getText());
        sWarnaFrame = String.valueOf(spinWarnaFrame.getSelectedItem());
    }

    // method untuk menyimpan data input dari pengguna pada setiap kategori
    private void setKonfirmasiData() {
        switch (pesananMasuk) {
            //mengirim data yang dimasukan pengguna ke BonBarang
            //-------------------------------------------KATEGORI DOKUMEN------------------------------------------------
            case "Laporan":
                if (jmlData.equalsIgnoreCase("12")) {
                    spesifikasiBarang = new String[]{sJmlHalaman, sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sPosisiJilid, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                } else if (jmlData.equalsIgnoreCase("14")) {
                    spesifikasiBarang = new String[]{sJmlHalaman, sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sPosisiJilid, sCoverDepan, sCoverBelakang, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                } else {
                    spesifikasiBarang = new String[]{sJmlHalaman, sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                }
                break;
            case "Buku":
                spesifikasiBarang = new String[]{sJmlHalaman, sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sPosisiJilid, sJenisLaminasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Berkas":
                if (jmlData.equalsIgnoreCase("12")) {
                    spesifikasiBarang = new String[]{sJmlHalaman, sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sPosisiJilid, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                } else {
                    spesifikasiBarang = new String[]{sJmlHalaman, sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                }
                break;
            case "Sertifikat":
                spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sJenisLaminasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Portofolio":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sPosisiJilid, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Skripsi":
                spesifikasiBarang = new String[]{sJmlHalaman, sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Buku Lipat":
                spesifikasiBarang = new String[]{sJmlHalaman, sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sPosisiJilid, sCoverDepan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Notebook":
                spesifikasiBarang = new String[]{sJmlHalamanSpinner, sUkuranKertas, sJenisKertas, sPilihanJilid, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Nota":
                spesifikasiBarang = new String[]{sJmlHalamanSpinner, sPilihWarna, sUkuranKertas, sJenisKertas, sOrientasi, sPilihanJilid, sCoverDepan, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Kop Surat":
                spesifikasiBarang = new String[]{sJmlHalamanSpinner, sPilihWarna, sUkuranKertas, sJenisKertas, sCoverDepan, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Map":
                spesifikasiBarang = new String[]{sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sJenisLaminasi, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Amplop":
                spesifikasiBarang = new String[]{sJmlHalamanSpinner, sPilihWarna, sJenisKertas, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Pas Foto":
                spesifikasiBarang = new String[]{sDuaTiga, sTigaEmpat, sEmpatEnam, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Foto":
                if (jmlData.equalsIgnoreCase("7")) {
                    spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sFrame, sWarnaFrame, sWaktuProses, sHargaSatuan, sHargaTotal};
                } else {
                    spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sFrame, sWaktuProses, sHargaSatuan, sHargaTotal};
                }

                break;
//      ---------------------------END KATEGORI DOKUMEN-------------------------------------
//      ---------------------------KATEGORI MEDIA PROMOSI------------------------------------
            case "Name Tag":
                spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Kartu Nama":
                spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sSisi, sJenisLaminasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Spanduk":
                spesifikasiBarang = new String[]{sPanjang, sLebar, sJenisBahan, sFinishing, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Poster":
                spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sSisi, sJenisLaminasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Stand Banner":
                if (jmlData.equalsIgnoreCase("4")) {
                    spesifikasiBarang = new String[]{sSisi, sUkuranBahan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                } else {
                    spesifikasiBarang = new String[]{sSisi, sUkuranBahan, sJenisBahan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                }
                break;
            case "Flyer Ekonomis":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sWarnaCetak, sLipatan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Brosur Ekonomis":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sWarnaCetak, sLipatan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Baliho":
                spesifikasiBarang = new String[]{sPanjang, sLebar, sJenisBahan, sFinishing, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Booklet":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid, sPosisiJilid,
                        sJenisLaminasi, sCoverDepan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Katalog":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sPilihanJilid,
                        sPosisiJilid, sJenisLaminasi, sCoverDepan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Kain":
                spesifikasiBarang = new String[]{sPanjang, sLebar, sJenisBahan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Bendera":
                spesifikasiBarang = new String[]{sPanjang, sLebar, sJenisBahan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Poster Hitam Putih":
                spesifikasiBarang = new String[]{sPilihWarna, sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sJmlCetak, sWaktuProses
                        , sHargaSatuan, sHargaTotal};
                break;
            case "Poster Standard":
                spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sSisi, sOrientasi, sJenisLaminasi, sJmlCetak
                        , sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Brosur Hitam Putih":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sWarnaCetak, sLipatan,
                        sOrientasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Flyer Hitam Putih":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sWarnaCetak, sLipatan,
                        sOrientasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Brosur":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sWarnaCetak, sLipatan,
                        sOrientasi, sJenisLaminasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Flyer Standard":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sSisi, sWarnaCetak, sLipatan,
                        sOrientasi, sJenisLaminasi, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Stempel":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sWarnaCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Cutting Sticker":
                spesifikasiBarang = new String[]{sJmlHalaman, sUkuranKertas, sJenisKertas, sWarnaCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Sticker":
                spesifikasiBarang = new String[]{sJenisKertas, sJenisLaminasi, sPanjang, sLebar, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "ID Card":
                //isi data
                break;
            case "Tali ID Card":
                //isi data
                break;
            case "Topi":
                spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Kaos Satuan":
                if (jmlData.equalsIgnoreCase("13")) {
                    spesifikasiBarang = new String[]{sWarna, sDetilWarna, sJenisLaminasi, sJenisBahan, sBajuXs, sBajuS, sBajuM,
                            sbajuXl, sBajuXxl, sJenisCetak, sJumlah, sWaktuProses, sHargaSatuan, sHargaTotal};
                } else {
                    spesifikasiBarang = new String[]{sWarna, sJenisLaminasi, sJenisBahan, sBajuXs, sBajuS, sBajuM,
                            sbajuXl, sBajuXxl, sJenisCetak, sJumlah, sWaktuProses, sHargaSatuan, sHargaTotal};
                }
                break;
            case "Kaos Standard":
                spesifikasiBarang = new String[]{sWarna, sJenisLaminasi, sJenisBahan, sUkuranBaju, sJenisCetak, sJumlah, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Kaos Ekonomis":
                spesifikasiBarang = new String[]{sWarna, sJenisLaminasi, sJenisBahan, sUkuranBaju, sJenisCetak, sJumlah, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
//      --------------------------END MEDIA PROMOSI----------------------------------
//      --------------------------KATEGORI SOUVENIR----------------------------------
            case "Mug":
                if (jmlData.equalsIgnoreCase("3")) {
                    spesifikasiBarang = new String[]{sJenisBahan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                } else {
                    spesifikasiBarang = new String[]{sJenisBahan, sWarnaBahan, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                }
                break;
            case "Bolpoin":
                spesifikasiBarang = new String[]{sUkuranBahan, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Pin":
                spesifikasiBarang = new String[]{sJenisBahan, sBahan, sJumlah, sFinishing, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Gantungan kunci":
                spesifikasiBarang = new String[]{sSisi, sJenisBahan, sBahan, sJumlah, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Tumbler":
                spesifikasiBarang = new String[]{sUkuranBahan, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Gantungan kunci Akrilik":
                spesifikasiBarang = new String[]{sUkuranBahan, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Kalender Dinding":
                spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Kalender Meja":
                spesifikasiBarang = new String[]{sUkuranKertas, sJenisKertas, sJmlCetak, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Payung":
                spesifikasiBarang = new String[]{sJenisBahan, sFinishing, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Jam Dinding":
                spesifikasiBarang = new String[]{sUkuranBahan, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Tas Kresek":
                spesifikasiBarang = new String[]{sUkuranBahan, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;
            case "Kipas":
                spesifikasiBarang = new String[]{sUkuranBahan, sWaktuProses, sHargaSatuan, sHargaTotal};
                break;

        }
    }

    // method handle kembali ke halaman utama
    private void exitBackKey() {
        new AlertDialog.Builder(this)
                .setMessage("Batal memesan " + pesananMasuk + "?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        exitBackKey();
    }
}

