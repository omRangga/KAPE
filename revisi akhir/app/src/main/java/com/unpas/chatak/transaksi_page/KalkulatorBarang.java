package com.unpas.chatak.transaksi_page;

import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import com.unpas.chatak.R;
import com.unpas.chatak.model.BarangModel;

public class KalkulatorBarang {

    //memanggil hargaBarang
    private BarangModel hargaBarang;

    //membuat Constructor KalkulatorBarang
    public KalkulatorBarang() {
        hargaBarang = new BarangModel();
    }



    //method untuk menghitung harga satuan barang
    public void setHargaSatuan(String kategori, String namaBarang) {
        Log.d("isi kategori", kategori);
        //harga satuan dimulai dari ukuran kertas
        switch (kategori) {
            //case Cover Depan
            case "Tanpa Porporasi":
                hargaBarang.setdCoverDepan(0);
                hitungHargaNota();
                break;
            case "Dengan Porporasi":
                hargaBarang.setdCoverDepan(5000);
                hitungHargaNota();
                break;
            //case jml halaman spinner
            case "40":
                hargaBarang.setdJmlHalamanSpinner(200000);
                hitungHargaNotebook();
                break;
            case "80":
                hargaBarang.setdJmlHalamanSpinner(400000);
                hitungHargaNotebook();
                break;
            case "50 Lembar":
                hargaBarang.setdJmlHalamanSpinner(50000);
                hitungHargaNota();
            case "500 Lembar (1 Rim)":
                hargaBarang.setdJmlHalamanSpinner(125000);
                hitungHargaKopSurat();
                break;
            //case ukuran kertas
            case "2R (4 Lembar)":
                hargaBarang.setdUkuranKertas(9000);
                hitungHargaFoto();
                break;
            case "3R (4 Lembar)":
                hargaBarang.setdUkuranKertas(10000);
                hitungHargaFoto();
                break;
            case "4R (4 Lembar)":
                hargaBarang.setdUkuranKertas(12000);
                hitungHargaFoto();
                break;
            case "5R (4 Lembar)":
                hargaBarang.setdUkuranKertas(21000);
                hitungHargaFoto();
                break;
            case "8R":
            case "8RP":
                hargaBarang.setdUkuranKertas(23000);
                hitungHargaFoto();
                break;
            case "12R":
            case "12RP":
                hargaBarang.setdUkuranKertas(92000);
                hitungHargaFoto();
                break;
            case "16R":
            case "16RP":
                hargaBarang.setdUkuranKertas(150000);
                hitungHargaFoto();
                break;
            case "20R":
            case "20RP":
                hargaBarang.setdUkuranKertas(207000);
                hitungHargaFoto();
                break;
            case "24R":
            case "24RP":
                hargaBarang.setdUkuranKertas(342000);
                hitungHargaFoto();
                break;
            case "PoliFlex Warna Biasa":
                hargaBarang.setdUkuranKertas(15000);
                hitungHargaTopi();
                break;
            case "PoliFlex Gold/Silver":
                hargaBarang.setdUkuranKertas(20000);
                hitungHargaTopi();
                break;
            case "PoliFlex Fosfor":
                hargaBarang.setdUkuranKertas(25000);
                hitungHargaTopi();
                break;
            case "Stempel Warna":
                if (namaBarang.equalsIgnoreCase("Stempel")) {
                    hargaBarang.setdUkuranKertas(30000);
                    hitungHargaStempel();
                } else if (namaBarang.equalsIgnoreCase("Cutting Sticker")) {
                    hargaBarang.setdUkuranKertas(30000);
                    hitungHargaCuttingSticker();
                }
                break;
            case "Stempel Trodat":
                if (namaBarang.equalsIgnoreCase("Stempel")) {
                    hargaBarang.setdUkuranKertas(40000);
                    hitungHargaStempel();
                } else if (namaBarang.equalsIgnoreCase("Cutting Sticker")) {
                    hargaBarang.setdUkuranKertas(40000);
                    hitungHargaCuttingSticker();
                }
                break;
            case "Stempel Kayu":
                if (namaBarang.equalsIgnoreCase("Stempel")) {
                    hargaBarang.setdUkuranKertas(50000);
                    hitungHargaStempel();
                } else if (namaBarang.equalsIgnoreCase("Cutting Sticker")) {
                    hargaBarang.setdUkuranKertas(50000);
                    hitungHargaCuttingSticker();
                }
                break;
            case "B4 (150x105 Mm)":
            case "B3 (124x95 Mm)":
            case "B2 (124x80 Mm)":
                hargaBarang.setdUkuranKertas(2000);
                hitungHargaNameTag();
                break;
            case "Regular (9x5.5cm)":
            case "Slim (9x5cm)":
                hargaBarang.setdUkuranKertas(20000);
                hitungHargaKartuNama();
                break;
            case "A3+":
                if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hargaBarang.setdUkuranKertas(6000);
                    hitungHargaPosterStandard();
                }
                break;
            case "A3":
                if (namaBarang.equalsIgnoreCase("Poster")) {
                    hargaBarang.setdUkuranKertas(10000);
                    hitungHargaPoster();
                } else if (namaBarang.equalsIgnoreCase("Poster Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(500);
                    hitungHargaPosterHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hargaBarang.setdUkuranKertas(5000);
                    hitungHargaPosterStandard();
                }
                break;
            case "A4":
                hargaBarang.setdUkuranKertas(150);
                if (namaBarang.equalsIgnoreCase("Poster")) {
                    hargaBarang.setdUkuranKertas(5000);
                    hitungHargaPoster();
                } else if (namaBarang.equalsIgnoreCase("Flyer Ekonomis")) {
                    hargaBarang.setdUkuranKertas(400);
                    hitungHargaFlyerEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Ekonomis")) {
                    hargaBarang.setdUkuranKertas(400);
                    hitungHargaBrosurEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Booklet")) {
                    hargaBarang.setdUkuranKertas(4000);
                    hitungHargaBooklet();
                } else if (namaBarang.equalsIgnoreCase("Katalog")) {
                    hargaBarang.setdUkuranKertas(4000);
                    hitungHargaKatalog();
                } else if (namaBarang.equalsIgnoreCase("Poster Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(250);
                    hitungHargaPosterHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hargaBarang.setdUkuranKertas(4000);
                    hitungHargaPosterStandard();
                } else if (namaBarang.equalsIgnoreCase("Brosur Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(250);
                    hitungHargaBrosurHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Flyer Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(250);
                    hitungHargaFlyerHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdUkuranKertas(2);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdUkuranKertas(2);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hargaBarang.setdUkuranKertas(2);
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdUkuranKertas(2500);
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdUkuranKertas(2);
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Buku Lipat")) {
                    hargaBarang.setdUkuranKertas(2);
                    hitungHargaBukuLipat();
                }
                break;
            case "A4 (2 Pcs)":
                if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdUkuranKertas(500);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdUkuranKertas(500);
                    hitungHargaFlyerStandard();
                }
                break;
            case "A5":
                hargaBarang.setdUkuranKertas(125);
                if (namaBarang.equalsIgnoreCase("Flyer Ekonomis")) {
                    hargaBarang.setdUkuranKertas(200);
                    hitungHargaFlyerEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Ekonomis")) {
                    hargaBarang.setdUkuranKertas(200);
                    hitungHargaBrosurEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(125);
                    hitungHargaBrosurHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Flyer Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(125);
                    hitungHargaFlyerHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdUkuranKertas(1);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdUkuranKertas(1);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hargaBarang.setdUkuranKertas(1);
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdUkuranKertas(1500);
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdUkuranKertas(1);
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Buku Lipat")) {
                    hargaBarang.setdUkuranKertas(1);
                    hitungHargaBukuLipat();
                }
                break;
            case "A5 (4 Pcs)":
                if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdUkuranKertas(500);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdUkuranKertas(500);
                    hitungHargaFlyerStandard();
                }
                break;
            case "A6":
                if (namaBarang.equalsIgnoreCase("Brosur Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(100);
                    hitungHargaBrosurHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Flyer Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(100);
                    hitungHargaFlyerHitamPutih();
                }
                break;
            case "A6 (8 Pcs)":
                if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdUkuranKertas(800);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdUkuranKertas(800);
                    hitungHargaFlyerStandard();
                }
                break;
            case "1/3 A4":
                if (namaBarang.equalsIgnoreCase("Brosur Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(75);
                    hitungHargaBrosurHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Flyer Hitam Putih")) {
                    hargaBarang.setdUkuranKertas(75);
                    hitungHargaFlyerHitamPutih();
                }
                break;
            case "1/3 A4 (6 Pcs)":
                if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdUkuranKertas(450);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdUkuranKertas(450);
                    hitungHargaFlyerStandard();
                }
                break;
            case "20x15 Cm (250 Eksemplar)":
                hargaBarang.setdUkuranKertas(25000);
                hitungHargaKalenderMeja();
                break;
            case "26x38 (250 Eksemplar)":
                hargaBarang.setdUkuranKertas(3456);
                hitungHargaKalenderDinding();
                break;
            case "31x48 (250 Eksemplar)":
                hargaBarang.setdUkuranKertas(3744);
                hitungHargaKalenderDinding();
                break;
            case "38x53 (250 Eksemplar)":
                hargaBarang.setdUkuranKertas(4608);
                hitungHargaKalenderDinding();
                break;
            case "44x64 (250 Eksemplar)":
                hargaBarang.setdUkuranKertas(6264);
                hitungHargaKalenderDinding();
                break;
            case "ArtPaper 120 Gsm":
                if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdJenisKertas(0);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdJenisKertas(0);
                    hitungHargaFlyerStandard();
                }
                break;
            case "ArtPaper 150 Gsm":
                if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hargaBarang.setdJenisKertas(0);
                    hitungHargaPosterStandard();
                } else if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdJenisKertas(100);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdJenisKertas(100);
                    hitungHargaFlyerStandard();
                }
                break;
            case "ArtPaper 260 Gsm":
                hargaBarang.setdJenisKertas(1000);
                if (namaBarang.equalsIgnoreCase("Poster")) {
                    hargaBarang.setdJenisKertas(2000);
                    hitungHargaPoster();
                } else if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hargaBarang.setdJenisKertas(3000);
                    hitungHargaPosterStandard();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdJenisKertas(4000);
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Buku Lipat")) {
                    hargaBarang.setdCoverDepan(7000);
                    hitungHargaBukuLipat();
                }
//                hitungHargaNameTag();
//                hitungHargaKalenderMeja();
                break;
            case "ArtPaper 210 Gsm":
                hargaBarang.setdJenisKertas(0);
                if (namaBarang.equalsIgnoreCase("Name Tag")) {
                    hitungHargaNameTag();
                } else if (namaBarang.equalsIgnoreCase("Poster")) {
                    hargaBarang.setdJenisKertas(1000);
                    hitungHargaPoster();
                } else if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hargaBarang.setdJenisKertas(2000);
                    hitungHargaPosterStandard();
                }
                if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdJenisKertas(200);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdJenisKertas(200);
                    hitungHargaFlyerStandard();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdJenisKertas(2000);
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Portofolio")) {
                    hargaBarang.setdJenisKertas(7000);
                    hitungHargaPortofolio();
                }
                break;
            case "Artpaper 260 Gsm 1 Halaman":
                hargaBarang.setdJenisKertas(0);
                hitungHargaKalenderDinding();
                break;
            case "Artpaper 120 Gsm 2 Halaman":
                hargaBarang.setdJenisKertas(864);
                hitungHargaKalenderDinding();
                break;
            case "Artpaper 120 Gsm 3 Halaman":
                hargaBarang.setdJenisKertas(3024);
                hitungHargaKalenderDinding();
                break;
            case "Artpaper 120 Gsm 4 Halaman":
                hargaBarang.setdJenisKertas(3600);
                hitungHargaKalenderDinding();
                break;
            case "Artpaper 120 Gsm 6 Halaman":
                hargaBarang.setdJenisKertas(4608);
                hitungHargaKalenderDinding();
                break;
            case "Artpaper 120 Gsm 12 Halaman":
                hargaBarang.setdJenisKertas(10800);
                hitungHargaKalenderDinding();
                break;
            //case ukuran bahan
            case "X Banner 160x60 Cm":
            case "Y Banner 160x60 Cm":
                hargaBarang.setdUkuranBahan(75000);
                hitungHargaStandBanner();
                break;
            case "X Banner 160x80 Cm":
            case "Y Banner 160x80 Cm":
                hargaBarang.setdUkuranBahan(90000);
                hitungHargaStandBanner();
                break;
            case "Roll Up Banner 160x60 Cm":
                hargaBarang.setdUkuranBahan(260000);
                hitungHargaStandBanner();
                break;
            case "Roll Up Banner 200x80 Cm":
                hargaBarang.setdUkuranBahan(290000);
                hitungHargaStandBanner();
                break;
            case "Kipas A (Jadi 100 Pcs)":
                hargaBarang.setdUkuranBahan(5000);
                hitungHargaKipas();
                break;
            case "Kipas B (Jadi 100 Pcs)":
                hargaBarang.setdUkuranBahan(6000);
                hitungHargaKipas();
                break;
            case "Kipas C (Jadi 100 Pcs)":
                hargaBarang.setdUkuranBahan(7000);
                hitungHargaKipas();
                break;
            case "Bolpoin A (Jadi 10 Pcs)":
            case "Bolpoin B (Jadi 10 Pcs)":
            case "Bolpoin C (Jadi 10 Pcs)":
                hargaBarang.setdUkuranBahan(5000);
                hitungHargaBolpoin();
                break;
            case "Bolpoin A (Jadi 50 Pcs)":
            case "Bolpoin B (Jadi 50 Pcs)":
            case "Bolpoin C (Jadi 50 Pcs)":
                hargaBarang.setdUkuranBahan(4000);
                hitungHargaBolpoin();
                break;
            //case jenis bahan
            case "Softcombed 30 S":
                hargaBarang.setdJenisBahan(10000);
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hitungHargaKaosSatuan();
                } else if (namaBarang.equalsIgnoreCase("Kaos Standard")) {
                    hitungHargaKaosStandard();
                } else if (namaBarang.equalsIgnoreCase("Kaos Ekonomis")) {
                    hargaBarang.setdJenisBahan(5000);
                    hitungHargaKaosEkonomis();
                }
                break;
            case "Softcombed 24 S":
                hargaBarang.setdJenisBahan(8000);
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hitungHargaKaosSatuan();
                } else if (namaBarang.equalsIgnoreCase("Kaos Standard")) {
                    hitungHargaKaosStandard();
                } else if (namaBarang.equalsIgnoreCase("Kaos Ekonomis")) {
                    hargaBarang.setdJenisBahan(4000);
                    hitungHargaKaosEkonomis();
                }
                break;
            case "Softcombed 20 S":
            case "Cardet":
                hargaBarang.setdJenisBahan(5000);
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hitungHargaKaosSatuan();
                } else if (namaBarang.equalsIgnoreCase("Kaos Standard")) {
                    hitungHargaKaosStandard();
                } else if (namaBarang.equalsIgnoreCase("Kaos Ekonomis")) {
                    hargaBarang.setdJenisBahan(3000);
                    hitungHargaKaosEkonomis();
                }
            case "Kain Satin":
                hargaBarang.setdJenisBahan(85000);
                if (namaBarang.equalsIgnoreCase("Kain")) {
                    hitungHargaKain();
                } else if (namaBarang.equalsIgnoreCase("Bendera")) {
                    hitungHargaBendera();
                }
                break;
            case "Flexy China":
                hargaBarang.setdJenisBahan(17000);
                if (namaBarang.equalsIgnoreCase("Spanduk")) {
                    hitungHargaSpanduk();
                } else if (namaBarang.equalsIgnoreCase("Baliho")) {
                    hitungHargaBaliho();
                }
                break;
            case "Flexy Korea":
                hargaBarang.setdJenisBahan(25000);
                if (namaBarang.equalsIgnoreCase("Spanduk")) {
                    hitungHargaSpanduk();
                } else if (namaBarang.equalsIgnoreCase("Baliho")) {
                    hitungHargaBaliho();
                }
                break;
            case "Korcin Tebal":
                hargaBarang.setdJenisBahan(35000);
                if (namaBarang.equalsIgnoreCase("Spanduk")) {
                    hitungHargaSpanduk();
                } else if (namaBarang.equalsIgnoreCase("Baliho")) {
                    hitungHargaBaliho();
                }
                break;
            case "Mug Putih":
                hargaBarang.setdJenisBahan(20000);
                hitungHargaMugg();
                break;
            case "Mug Warna Dalam":
            case "Mug Warna Luar":
            case "Mug Love Putih":
            case "Mug Transparan":
                hargaBarang.setdJenisBahan(25000);
                hitungHargaMugg();
                break;
            case "Mug Love Warna Luar":
                hargaBarang.setdJenisBahan(30000);
                hitungHargaMugg();
                break;
            case "Mug Bunglon":
            case "Mug Kembung":
                hargaBarang.setdJenisBahan(50000);
                hitungHargaMugg();
                break;
            case "Payung Golf (100 Pcs)":
                hargaBarang.setdJenisBahan(10000);
                hitungHargaPayung();
                break;
            case "Payung Panjang (100 Pcs)":
                hargaBarang.setdJenisBahan(12000);
                hitungHargaPayung();
                break;
            case "Payung Lipat 2 (100 Pcs)":
                hargaBarang.setdJenisBahan(14000);
                hitungHargaPayung();
                break;
            case "Payung Lipat 3 (100 Pcs)":
                hargaBarang.setdJenisBahan(16000);
                hitungHargaPayung();
                break;
            case "Payung Sakura (100 Pcs)":
                hargaBarang.setdJenisBahan(18000);
                hitungHargaPayung();
                break;
            case "Payung Botol (100 Pcs)":
                hargaBarang.setdJenisBahan(20000);
                hitungHargaPayung();
                break;
            case "Tas Kresek 32x21 Cm (1000 Pcs)":
            case "Tas Kresek 38x28 Cm (1000 Pcs)":
                hargaBarang.setdUkuranBahan(1200);
                hitungHargaTasKresek();
                break;
            case "Jam Dinding 20Cm":
                hargaBarang.setdUkuranBahan(50000);
                hitungHargaJamDinding();
                break;
            case "Jam Dinding 26Cm":
                hargaBarang.setdUkuranBahan(60000);
                hitungHargaJamDinding();
                break;
            case "Jam Dinding 32Cm":
                hargaBarang.setdUkuranBahan(70000);
                hitungHargaJamDinding();
                break;
            case "Jam Dinding 36Cm":
                hargaBarang.setdUkuranBahan(80000);
                hitungHargaJamDinding();
                break;
            case "Akrilik 15 Cm2":
                hargaBarang.setdUkuranBahan(2000);
                hitungHargaGantunganKunciAkrilik();
                break;
            case "Akrilik 20 Cm2":
                hargaBarang.setdUkuranBahan(3000);
                hitungHargaGantunganKunciAkrilik();
                break;
            case "Akrilik 25 Cm2":
                hargaBarang.setdUkuranBahan(4000);
                hitungHargaGantunganKunciAkrilik();
                break;
            case "Tumbler Stainless":
                hargaBarang.setHargaSatuan(80000);
                hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
                break;
            case "Tumbler Plastik Besar":
                hargaBarang.setHargaSatuan(60000);
                hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
                break;
            case "Tumbler Plastik Kecil":
                hargaBarang.setHargaSatuan(40000);
                hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
                break;
            case "Ganci 44 Mm":
                hargaBarang.setdJenisBahan(2000);
                hitungHargaGantunganKunci();
                break;
            case "Ganci 58 Mm":
                hargaBarang.setdJenisBahan(2250);
                hitungHargaGantunganKunci();
                break;
            case "Pin 44 Mm":
                hargaBarang.setdJenisBahan(2000);
                hitungHargaPin();
                break;
            case "Pin 58 Mm":
                hargaBarang.setdJenisBahan(2250);
                hitungHargaPin();
                break;
            //case sisi
            case "1 sisi":
                //handle bug menangkap data sisi
                if (namaBarang.equalsIgnoreCase("Gantungan Kunci")) {
                    hargaBarang.setdSisi(1);
                    hitungHargaGantunganKunci();
                } else if (namaBarang.equalsIgnoreCase("Name Tag")) {
                    hargaBarang.setdSisi(1);
                    hitungHargaNameTag();
                } else if (namaBarang.equalsIgnoreCase("Kartu Nama")) {
                    hargaBarang.setdSisi(1);
                    hitungHargaKartuNama();
                } else if (namaBarang.equalsIgnoreCase("Flyer Ekonomis")) {
                    hargaBarang.setdSisi(50);
                    hitungHargaFlyerEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Ekonomis")) {
                    hargaBarang.setdSisi(50);
                    hitungHargaBrosurEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Hitam Putih")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaBrosurHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Flyer Hitam Putih")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaFlyerHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaFlyerStandard();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdSisi(50);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hargaBarang.setdSisi(50);
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdSisi(2);
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Map")) {
                    hargaBarang.setdSisi(2275000);
                    hitungHargaMap();
                }
                break;
            case "2 sisi":
                hargaBarang.setdSisi(2);
                if (namaBarang.equalsIgnoreCase("Gantungan Kunci")) {
                    hitungHargaGantunganKunci();
                } else if (namaBarang.equalsIgnoreCase("Name Tag")) {
                    hargaBarang.setdSisi(hargaBarang.getdSisi() - 0.5);
                    hitungHargaNameTag();
                } else if (namaBarang.equalsIgnoreCase("Kartu Nama")) {
                    hitungHargaKartuNama();
                } else if (namaBarang.equalsIgnoreCase("Flyer Ekonomis")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaFlyerEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Ekonomis")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaBrosurEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Hitam Putih")) {
                    hargaBarang.setdSisi(50);
                    hitungHargaBrosurHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Flyer Hitam Putih")) {
                    hargaBarang.setdSisi(50);
                    hitungHargaFlyerHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdSisi(100);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdSisi(100);
                    hitungHargaFlyerStandard();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hargaBarang.setdSisi(0);
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdSisi(1000);
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdSisi(1);
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Map")) {
                    hargaBarang.setdSisi(2600000);
                    hitungHargaMap();
                }
                break;
            //case Bahan || jenis kertas
            case "Putih Standar 11x23cm":
                if (namaBarang.equalsIgnoreCase("Amplop")) {
                    hargaBarang.setdJenisKertas(0);
                    hitungHargaAmplop();
                }
                break;
            case "Putih Folio 23x35cm":
                hargaBarang.setdJenisKertas(25000);
                hitungHargaAmplop();
                break;
            case "Coklat Folio 23x35cm":
                hargaBarang.setdJenisKertas(31500);
                hitungHargaAmplop();
                break;
            case "Linen":
                if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdJenisKertas(0);
                    hitungHargaSertifikat();
                }
                break;
            case "Concord":
                if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdJenisKertas(1500);
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Portofolio")) {
                    hargaBarang.setdJenisKertas(8000);
                    hitungHargaPortofolio();
                }
                break;
            case "Manila":
                if (namaBarang.equalsIgnoreCase("Portofolio")) {
                    hargaBarang.setdJenisKertas(5000);
                    hitungHargaPortofolio();
                }
                break;
            case "Rapel":
                hargaBarang.setdJenisKertas(5000);
                hitungHargaTopi();
                break;
            case "Twin":
                hargaBarang.setdJenisKertas(10000);
                hitungHargaTopi();
                break;
            case "Drill":
                hargaBarang.setdJenisKertas(15000);
                hitungHargaTopi();
                break;
            case "Glossy":
                if (namaBarang.equalsIgnoreCase("Poster")) {
                    hargaBarang.setdJenisKertas(0);
                    hitungHargaPoster();
                } else if (namaBarang.equalsIgnoreCase("Portofolio")) {
                    hargaBarang.setdJenisKertas(10000);
                }
                break;
            case "Stiker Vinil":
                hargaBarang.setdJenisKertas(2000);
                hitungHargaSticker();
                break;
            case "Stiker Oneway":
                hargaBarang.setdJenisKertas(3000);
                hitungHargaSticker();
                break;
            case "Stiker Transparan":
                hargaBarang.setdJenisKertas(5000);
                hitungHargaSticker();
                break;
            case "Doff":
                break;
            case "Canvas":
                break;
            //case jenis laminasi
            case "Laminasi Biasa":
                if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdJenisLaminasi(1000);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdJenisLaminasi(1000);
                    hitungHargaSertifikat();
                }

                break;
            case "Laminasi Glossy":
            case "Laminasi Doff":
                if (namaBarang.equalsIgnoreCase("Poster")) {
                    hargaBarang.setdJenisLaminasi(3);
                    hitungHargaPoster();
                } else if (namaBarang.equalsIgnoreCase("Kartu Nama")) {
                    hargaBarang.setdJenisLaminasi(2);
                    hitungHargaKartuNama();
                } else if (namaBarang.equalsIgnoreCase("Booklet")) {
                    hargaBarang.setdJenisLaminasi(5000);
                    hitungHargaBooklet();
                } else if (namaBarang.equalsIgnoreCase("Katalog")) {
                    hargaBarang.setdJenisLaminasi(6000);
                    hitungHargaKatalog();
                } else if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hargaBarang.setdJenisLaminasi(3000);
                    hitungHargaPosterStandard();
                } else if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdJenisLaminasi(2000);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdJenisLaminasi(2000);
                    hitungHargaFlyerStandard();
                } else if (namaBarang.equalsIgnoreCase("Sticker")) {
                    hargaBarang.setdJenisLaminasi(2000);
                    hitungHargaSticker();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdJenisLaminasi(500);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Map")) {
                    hargaBarang.setdJenisLaminasi(1000000);
                    hitungHargaMap();
                }
                break;
            case "Tanpa Laminasi":
                if (namaBarang.equalsIgnoreCase("Kartu Nama")) {
                    hargaBarang.setdJenisLaminasi(1);
                    hitungHargaKartuNama();
                } else if (namaBarang.equalsIgnoreCase("Poster")) {
                    hargaBarang.setdJenisLaminasi(1);
                    hitungHargaPoster();
                } else if (namaBarang.equalsIgnoreCase("Booklet")) {
                    hargaBarang.setdJenisLaminasi(0);
                    hitungHargaBooklet();
                } else if (namaBarang.equalsIgnoreCase("Katalog")) {
                    hargaBarang.setdJenisLaminasi(0);
                    hitungHargaKatalog();
                } else if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hargaBarang.setdJenisKertas(0);
                    hitungHargaPosterStandard();
                } else if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hargaBarang.setdJenisLaminasi(0);
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hargaBarang.setdJenisLaminasi(0);
                    hitungHargaFlyerStandard();
                } else if (namaBarang.equalsIgnoreCase("Sticker")) {
                    hargaBarang.setdJenisLaminasi(0);
                    hitungHargaSticker();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hargaBarang.setdJenisLaminasi(0);
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Map")) {
                    hargaBarang.setdJenisLaminasi(0);
                    hitungHargaMap();
                }
                break;
            case "Lengan Pendek":
                hargaBarang.setdJenisLaminasi(0);
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hitungHargaKaosSatuan();
                } else if (namaBarang.equalsIgnoreCase("Kaos Standard")) {
                    hitungHargaKaosStandard();
                } else if (namaBarang.equalsIgnoreCase("Kaos Ekonomis")) {
                    hitungHargaKaosEkonomis();
                }

                break;
            case "Lengan Panjang":
                hargaBarang.setdJenisLaminasi(15000);
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hitungHargaKaosSatuan();
                } else if (namaBarang.equalsIgnoreCase("Kaos Standard")) {
                    hitungHargaKaosStandard();
                } else if (namaBarang.equalsIgnoreCase("Kaos Ekonomis")) {
                    hargaBarang.setdJenisLaminasi(10000);
                    hitungHargaKaosEkonomis();
                }
                break;
            //case jenis cetak
            case "DTG Full Warna A4":
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hargaBarang.setdJenisCetak(5000);
                    hitungHargaKaosSatuan();
                }
                break;
            case "DTG Full Warna A3":
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hargaBarang.setdJenisCetak(10000);
                    hitungHargaKaosSatuan();
                }
                break;
            case "PoliFlex 1 Warna A3":
            case "PoliFlex 2 Warna A3":
            case "PoliFlex 3 Warna A3":
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hargaBarang.setdJenisCetak(15000);
                    hitungHargaKaosSatuan();
                }
                break;
            case "Kaos Reguler":
                hargaBarang.setdJenisCetak(5000);
                hitungHargaKaosEkonomis();
                break;
            case "Kaos Full Warna":
                hargaBarang.setdJenisCetak(10000);
                hitungHargaKaosEkonomis();
                break;
            //case pilihan jilid
            case "Hekter":
                hargaBarang.setdPilihanJilid(0);
                if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hitungHargaLaporan();
                }
                break;
            case "Biasa/Lakban":
                hargaBarang.setdPilihanJilid(2000);
                if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hitungHargaLaporan();
                }
                break;
            case "Biasa":
                if (namaBarang.equalsIgnoreCase("Notebook")) {
                    hargaBarang.setdPilihanJilid(0);
                    hitungHargaNotebook();
                }
                break;
            case "Ring Kawat":
                if (namaBarang.equalsIgnoreCase("Katalog")) {
                    hargaBarang.setdPilihanJilid(4000);
                    hitungHargaKatalog();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdPilihanJilid(8000);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdPilihanJilid(4000);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Portofolio")) {
                    hargaBarang.setdPilihanJilid(8000);
                    hitungHargaPortofolio();
                } else if (namaBarang.equalsIgnoreCase("Notebook")) {
                    hargaBarang.setdPilihanJilid(10000);
                    hitungHargaNotebook();
                }

                break;
            case "Ring Plastik":
                if (namaBarang.equalsIgnoreCase("Katalog")) {
                    hargaBarang.setdPilihanJilid(2000);
                    hitungHargaKatalog();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdPilihanJilid(4000);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdPilihanJilid(2000);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Portofolio")) {
                    hargaBarang.setdPilihanJilid(4000);
                    hitungHargaPortofolio();
                }
                break;
            case "HandCover Tinta Emas":
                if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdPilihanJilid(17000);
                    hitungHargaSkripsi();
                }
                break;
            case "HandCover Tinta Warna":
                if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdPilihanJilid(15000);
                    hitungHargaSkripsi();
                }
                break;
            case "SoftCover":
                if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdPilihanJilid(10000);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdPilihanJilid(12000);
                    hitungHargaSkripsi();
                }
                break;
            //case warna | pilih warna
            case "1 Warna":
                if (namaBarang.equalsIgnoreCase("Nota")) {
                    hargaBarang.setdPilihWarna(6000);
                    hitungHargaNota();
                } else if (namaBarang.equalsIgnoreCase("Amplop")) {
                    hargaBarang.setdPilihWarna(35000);
                    hitungHargaAmplop();
                }
                break;
            case "2 Warna":
                if (namaBarang.equalsIgnoreCase("Nota")) {
                    hargaBarang.setdPilihWarna(8000);
                    hitungHargaNota();
                } else if (namaBarang.equalsIgnoreCase("Amplop")) {
                    hargaBarang.setdPilihWarna(45000);
                    hitungHargaAmplop();
                }
                break;
            case "3 Warna":
                if (namaBarang.equalsIgnoreCase("Nota")) {
                    hargaBarang.setdPilihWarna(10000);
                    hitungHargaNota();
                } else if (namaBarang.equalsIgnoreCase("Amplop")) {
                    hargaBarang.setdPilihWarna(50000);
                    hitungHargaAmplop();
                }
                break;
            case "Full Warna":
                if (namaBarang.equalsIgnoreCase("Amplop")) {
                    hargaBarang.setdPilihWarna(65000);
                    hitungHargaAmplop();
                }
                break;
            case "Putih":
            case "Hitam":
                hargaBarang.setdWarna(20000);
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hitungHargaKaosSatuan();
                } else if (namaBarang.equalsIgnoreCase("Kaos Standard")) {
                    hitungHargaKaosStandard();
                } else if (namaBarang.equalsIgnoreCase("Kaos Ekonomis")) {
                    hargaBarang.setdWarna(10000);
                    hitungHargaKaosEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdPilihWarna(150);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdPilihWarna(150);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hargaBarang.setdPilihWarna(150);
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdPilihWarna(150);
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Buku Lipat")) {
                    hargaBarang.setdPilihWarna(150);
                    hitungHargaBukuLipat();
                }
            case "Laser Hitam":
                if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdPilihWarna(125);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdPilihWarna(125);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hargaBarang.setdPilihWarna(125);
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdPilihWarna(125);
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Buku Lipat")) {
                    hargaBarang.setdPilihWarna(125);
                    hitungHargaBukuLipat();
                }
                break;
            case "Warna":
            case "Raglan":
                hargaBarang.setdWarna(35000);
                if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hitungHargaKaosSatuan();
                } else if (namaBarang.equalsIgnoreCase("Kaos Standard")) {
                    hitungHargaKaosStandard();
                } else if (namaBarang.equalsIgnoreCase("Kaos Ekonomis")) {
                    hargaBarang.setdWarna(20000);
                    hitungHargaKaosEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hargaBarang.setdPilihWarna(200);
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hargaBarang.setdPilihWarna(200);
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hargaBarang.setdPilihWarna(200);
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hargaBarang.setdPilihWarna(200);
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Buku Lipat")) {
                    hargaBarang.setdPilihWarna(200);
                    hitungHargaBukuLipat();
                }
                break;
            //case ukuran foto
            case "Tidak Dicetak":
                hargaBarang.setdDuaTiga(0);
                hitungHargaPasFoto();
                break;
            case "4 Lembar":
            case "Tidak dicetak":
                hargaBarang.setdDuaTiga(2000);
                hargaBarang.setdTigaEmpat(3000);
                hargaBarang.setdEmpatEnam(5000);
                hitungHargaPasFoto();
                break;
            case "8 Lembar":
                hargaBarang.setdDuaTiga(4000);
                hargaBarang.setdTigaEmpat(6000);
                hargaBarang.setdEmpatEnam(10000);
                hitungHargaPasFoto();
                break;
            case "12 Lembar":
                hargaBarang.setdDuaTiga(6000);
                hargaBarang.setdTigaEmpat(9000);
                hargaBarang.setdEmpatEnam(15000);
                hitungHargaPasFoto();
                break;
            //case Frame
            case "Tanpa Frame":
                hargaBarang.setdFrame(0);
                hitungHargaFoto();
                break;
            case "Frame Minimalis":
                hargaBarang.setdFrame(10000);
                hitungHargaFoto();
                Log.d("isi harga frame", String.valueOf(hargaBarang.getdFrame()));
                break;
        }
    }


    //method untuk menghitung total harga setiap barang (harga satuan + jumLahBarang)
    //MENGHITUNG KATEGORI SOUVENIR
    //menghitung harga kipas
    private void hitungHargaKipas() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranBahan());
        Log.d("isi harga kipas", String.valueOf(hargaBarang.getdUkuranBahan()));
        hargaBarang.setHargaTotal(hargaBarang.getdUkuranBahan() * 100);
        Log.d("isi harga total kipas", String.valueOf(hargaBarang.getdUkuranBahan() * 100));
    }

    //menghitung harga payung
    private void hitungHargaPayung() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJenisBahan());
        Log.d("isi harga kipas", String.valueOf(hargaBarang.getdJenisBahan()));
        hargaBarang.setHargaTotal(hargaBarang.getdJenisBahan() * 100);
        Log.d("isi harga kipas", String.valueOf(hargaBarang.getdJenisBahan() * 100));
    }

    //menghitung harga tas kresek
    private void hitungHargaTasKresek() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranBahan());
        hargaBarang.setHargaTotal(hargaBarang.getdUkuranBahan() * 1000);
        Log.d("isi harga satuan", String.valueOf(hargaBarang.getHargaSatuan()));
        Log.d("isi harga total", String.valueOf(hargaBarang.getHargaTotal()));
    }

    //menghitung harga jam dinding
    private void hitungHargaJamDinding() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranBahan());
        hargaBarang.setHargaTotal(hargaBarang.getdUkuranBahan() * 1);
        Log.d("isi harga satuan", String.valueOf(hargaBarang.getHargaSatuan()));
        Log.d("isi harga total", String.valueOf(hargaBarang.getHargaTotal()));
    }

    //menghitung harga kalender meja
    private void hitungHargaKalenderMeja() {
        if (hargaBarang.getdJenisKertas() == 1000) {
            hargaBarang.setdJenisKertas(0);
            hargaBarang.setHargaSatuan(hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas());
        }
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas());
        hargaBarang.setHargaTotal((hargaBarang.getHargaSatuan() * 250) * hargaBarang.getdJmlCetak());
        Log.d("isi harga satuan", String.valueOf(hargaBarang.getHargaSatuan()));
        Log.d("isi harga total", String.valueOf(hargaBarang.getHargaTotal()));
    }

    //menghitung harga kalender dinding
    private void hitungHargaKalenderDinding() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * 250);
        Log.d("isi harga satuan", String.valueOf(hargaBarang.getHargaSatuan()));
        Log.d("isi harga total", String.valueOf(hargaBarang.getHargaTotal()));
    }

    //menghitung harga gantungan kunci akrilik
    private void hitungHargaGantunganKunciAkrilik() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranBahan());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
    }

    //menghitung harga gantungan kunci
    private void hitungHargaGantunganKunci() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJenisBahan() + hargaBarang.getdBahan()) / hargaBarang.getdSisi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJumlah());
        Log.d("isi harga sisi", String.valueOf(hargaBarang.getdSisi()));
        Log.d("isi harga satuan", String.valueOf(hargaBarang.getHargaSatuan()));
        Log.d("isi harga total", String.valueOf(hargaBarang.getHargaTotal()));
    }

    //menghitung harga Pin
    private void hitungHargaPin() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJenisBahan() + hargaBarang.getdBahan() + hargaBarang.getdFinishing());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJumlah());
        Log.d("isi harga satuan", String.valueOf(hargaBarang.getHargaSatuan()));
        Log.d("isi harga total", String.valueOf(hargaBarang.getHargaTotal()));
    }

    //menghitung harga Mug
    private void hitungHargaMugg() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJenisBahan());
        Log.d("isi jenis bahan", String.valueOf(hargaBarang.getdJenisBahan()));
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
        Log.d("isi harga satuan", String.valueOf(hargaBarang.getHargaSatuan()));
        Log.d("isi harga total", String.valueOf(hargaBarang.getHargaTotal()));
    }

    //menghitung harga bolpoin
    private void hitungHargaBolpoin() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranBahan());
        if (hargaBarang.getHargaSatuan() == 5000) {
            hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * 10);
        } else {
            hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * 50);
        }
    }
    //END HITUNG KATEGORI SOUVENIR

    //MENGHITUNG KATEGORI MEDIA PROMOSI
    //menghitung harga Name Tag
    private void hitungHargaNameTag() {
        hargaBarang.setHargaSatuan((hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas()) * hargaBarang.getdSisi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
        Log.d("isi harga satuan", String.valueOf(hargaBarang.getHargaSatuan()));
        Log.d("isi harga total", String.valueOf(hargaBarang.getHargaTotal()));
    }

    //menghitung harga kartu nama
    private void hitungHargaKartuNama() {
        hargaBarang.setHargaSatuan((hargaBarang.getdUkuranKertas() * hargaBarang.getdJenisLaminasi()) * hargaBarang.getdSisi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga spanduk
    private void hitungHargaSpanduk() {
        hargaBarang.setHargaSatuan(hargaBarang.getdPanjang() * hargaBarang.getdLebar() * hargaBarang.getdJenisBahan());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga poster
    private void hitungHargaPoster() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJenisKertas() + (hargaBarang.getdUkuranKertas() * hargaBarang.getdJenisLaminasi()));
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Stand Banner
    private void hitungHargaStandBanner() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranBahan());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga flyer ekonomis
    private void hitungHargaFlyerEkonomis() {
        hargaBarang.setHargaSatuan(((hargaBarang.getdUkuranKertas() + hargaBarang.getdSisi()) * hargaBarang.getdJmlHalaman()));
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    private void hitungHargaBrosurEkonomis() {
        hargaBarang.setHargaSatuan(((hargaBarang.getdUkuranKertas() + hargaBarang.getdSisi()) * hargaBarang.getdJmlHalaman()));
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga baliho
    private void hitungHargaBaliho() {
        hargaBarang.setHargaSatuan(hargaBarang.getdPanjang() * hargaBarang.getdLebar() * hargaBarang.getdJenisBahan());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Booklet
    private void hitungHargaBooklet() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * hargaBarang.getdUkuranKertas()) + hargaBarang.getdJenisLaminasi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga katalog
    private void hitungHargaKatalog() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * hargaBarang.getdUkuranKertas()) + hargaBarang.getdPilihanJilid() + hargaBarang.getdJenisLaminasi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga kain
    private void hitungHargaKain() {
        hargaBarang.setHargaSatuan(hargaBarang.getdPanjang() * hargaBarang.getdLebar() * hargaBarang.getdJenisBahan());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Bendera
    private void hitungHargaBendera() {
        hargaBarang.setHargaSatuan(hargaBarang.getdPanjang() * hargaBarang.getdLebar() * hargaBarang.getdJenisBahan());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga poster hitam putih
    private void hitungHargaPosterHitamPutih() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranKertas());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga poster standard
    private void hitungHargaPosterStandard() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas() + hargaBarang.getdJenisLaminasi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga brosur hitam putih
    private void hitungHargaBrosurHitamPutih() {
        hargaBarang.setHargaSatuan((hargaBarang.getdUkuranKertas() + hargaBarang.getdSisi()) * hargaBarang.getdJmlHalaman());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Flyer Hitam Putih
    private void hitungHargaFlyerHitamPutih() {
        hargaBarang.setHargaSatuan((hargaBarang.getdUkuranKertas() + hargaBarang.getdSisi()) * hargaBarang.getdJmlHalaman());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga brosur
    private void hitungHargaBrosur() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJmlHalaman() * ((hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas() + hargaBarang.getdSisi() + hargaBarang.getdJenisLaminasi())));
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga flyer standar
    private void hitungHargaFlyerStandard() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJmlHalaman() * ((hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas() + hargaBarang.getdSisi() + hargaBarang.getdJenisLaminasi())));
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga stempel
    private void hitungHargaStempel() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * hargaBarang.getdUkuranKertas()) / 5);
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
    }

    //menghitung harga Cutting Sticker
    private void hitungHargaCuttingSticker() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * hargaBarang.getdUkuranKertas()) / 5);
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
        Log.d("isi jmlHalaman", String.valueOf(hargaBarang.getdJmlHalaman()));
    }

    //menghitung harga Sticker
    private void hitungHargaSticker() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJenisKertas() * (hargaBarang.getdPanjang() * hargaBarang.getdLebar()) + hargaBarang.getdJenisLaminasi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
        Log.d("isi KERTAS", String.valueOf(hargaBarang.getdJenisKertas()));
    }
    //menghitung harga ID CARD
    //menghitung Harga Tali ID Card

    //menghitung harga topi
    private void hitungHargaTopi() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga kaos satuan
    private void hitungHargaKaosSatuan() {
        hargaBarang.setHargaSatuan(hargaBarang.getdWarna() + hargaBarang.getdJenisBahan() + hargaBarang.getdJenisLaminasi() + hargaBarang.getdJenisCetak());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJumlah() * (hargaBarang.getdBajuXs() + hargaBarang.getdBajuS() + hargaBarang.getdBajuM() + hargaBarang.getDbajuXl() + hargaBarang.getdBajuXxl()));
    }

    //menghitung harga kaos standar
    private void hitungHargaKaosStandard() {
        hargaBarang.setHargaSatuan(hargaBarang.getdWarna() + hargaBarang.getdJenisBahan() + hargaBarang.getdJenisLaminasi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJumlah());
    }//menghitung harga kaos ekonomis

    private void hitungHargaKaosEkonomis() {
        hargaBarang.setHargaSatuan(hargaBarang.getdWarna() + hargaBarang.getdJenisBahan() + hargaBarang.getdJenisLaminasi() + hargaBarang.getdJenisCetak());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJumlah());
    }
    //END HITUNG KATEGORI MEDIA PROMOSI

    //MENGHITUNG KATEGORI DOKUMEN
    //menghitung harga laporan
    private void hitungHargaLaporan() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * ((hargaBarang.getdPilihWarna() * hargaBarang.getdUkuranKertas()) + hargaBarang.getdSisi())) + hargaBarang.getdPilihanJilid());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Buku
    private void hitungHargaBuku() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * (hargaBarang.getdPilihWarna() * hargaBarang.getdUkuranKertas())) + hargaBarang.getdPilihanJilid() + hargaBarang.getdJenisLaminasi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga berkas
    private void hitungHargaBerkas() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJmlHalaman() * ((hargaBarang.getdPilihWarna() * hargaBarang.getdUkuranKertas()) + hargaBarang.getdSisi()));
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga sertifikat
    private void hitungHargaSertifikat() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranKertas() + hargaBarang.getdJenisKertas() + hargaBarang.getdSisi() + hargaBarang.getdJenisLaminasi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Portofolio
    private void hitungHargaPortofolio() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * hargaBarang.getdJenisKertas()) + hargaBarang.getdPilihanJilid());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Skripsi
    private void hitungHargaSkripsi() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * ((hargaBarang.getdPilihWarna() + hargaBarang.getdUkuranKertas()) * hargaBarang.getdSisi())) + hargaBarang.getdPilihanJilid());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Buku Lipat
    private void hitungHargaBukuLipat() {
        hargaBarang.setHargaSatuan((hargaBarang.getdJmlHalaman() * (hargaBarang.getdPilihWarna() * hargaBarang.getdUkuranKertas())) + hargaBarang.getdCoverDepan());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }

    //menghitung harga Notebook
    private void hitungHargaNotebook() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJmlHalamanSpinner() + hargaBarang.getdPilihanJilid());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());

    }

    //menghitung harga nota
    private void hitungHargaNota() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJmlHalamanSpinner() + hargaBarang.getdPilihWarna() + hargaBarang.getdCoverDepan());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
    }

    //menghitung harga kop surat
    private void hitungHargaKopSurat() {
        hargaBarang.setHargaSatuan(hargaBarang.getdJmlHalamanSpinner());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
    }

    //menghitung harga Map
    private void hitungHargaMap() {
        hargaBarang.setHargaSatuan(hargaBarang.getdSisi() + hargaBarang.getdJenisLaminasi());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
    }

    //menghitung harga Amplop
    private void hitungHargaAmplop() {
        hargaBarang.setHargaSatuan(hargaBarang.getdPilihWarna() + hargaBarang.getdJenisKertas());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan() * hargaBarang.getdJmlCetak());
    }


    private void hitungHargaPasFoto() {
        hargaBarang.setHargaSatuan(hargaBarang.getdDuaTiga() + hargaBarang.getdTigaEmpat() + hargaBarang.getdEmpatEnam());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
    }

    //menghitung harga foto
    private void hitungHargaFoto() {
        hargaBarang.setHargaSatuan(hargaBarang.getdUkuranKertas() + hargaBarang.getdFrame());
        hargaBarang.setHargaTotal(hargaBarang.getHargaSatuan());
    }

    //return harga ke form pesanan masuk
    public double getHargaBarangSatuan() {
        return hargaBarang.getHargaSatuan();
    }

    public double getTotalHargaBarang() {
        return hargaBarang.getHargaTotal();
    }
    //end return

    //method untuk menghitung jumlahBarang
    public void hitungJumlahBarang(double jumlahBarang, EditText barEditTeks, String namaBarang) {

        switch (barEditTeks.getId()) {
            case R.id.value_jml_cetak:
                hargaBarang.setdJmlCetak(jumlahBarang);
                //handle perhitungan sesuai nama barang
                if (namaBarang.equalsIgnoreCase("Kalender Meja")) {
                    hitungHargaKalenderMeja();
                } else if (namaBarang.equalsIgnoreCase("Name Tag")) {
                    hitungHargaNameTag();
                } else if (namaBarang.equalsIgnoreCase("Mug")) {
                    hitungHargaMugg();
                } else if (namaBarang.equalsIgnoreCase("Kartu Nama")) {
                    hitungHargaKartuNama();
                } else if (namaBarang.equalsIgnoreCase("Spanduk")) {
                    hitungHargaSpanduk();
                } else if (namaBarang.equalsIgnoreCase("Poster")) {
                    hitungHargaPoster();
                } else if (namaBarang.equalsIgnoreCase("Stand Banner")) {
                    hitungHargaStandBanner();
                } else if (namaBarang.equalsIgnoreCase("Flyer Ekonomis")) {
                    hitungHargaFlyerEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Ekonomis")) {
                    hitungHargaBrosurEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Baliho")) {
                    hitungHargaBaliho();
                } else if (namaBarang.equalsIgnoreCase("Booklet")) {
                    hitungHargaBooklet();
                } else if (namaBarang.equalsIgnoreCase("Katalog")) {
                    hitungHargaKatalog();
                } else if (namaBarang.equalsIgnoreCase("Kain")) {
                    hitungHargaKain();
                } else if (namaBarang.equalsIgnoreCase("Bendera")) {
                    hitungHargaBendera();
                } else if (namaBarang.equalsIgnoreCase("Poster Hitam Putih")) {
                    hitungHargaPosterHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Poster Standard")) {
                    hitungHargaPosterStandard();
                } else if (namaBarang.equalsIgnoreCase("Brosur Hitam Putih")) {
                    hitungHargaBrosurHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Flyer Hitam Putih")) {
                    hitungHargaFlyerHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hitungHargaFlyerStandard();
                } else if (namaBarang.equalsIgnoreCase("Topi")) {
                    hitungHargaTopi();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Sertifikat")) {
                    hitungHargaSertifikat();
                } else if (namaBarang.equalsIgnoreCase("Portofolio")) {
                    hitungHargaPortofolio();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Buku Lipat")) {
                    hitungHargaBukuLipat();
                } else if (namaBarang.equalsIgnoreCase("Amplop")) {
                    hitungHargaAmplop();
                }

                Log.d("isi jmlCetak", String.valueOf(jumlahBarang));
                Log.d("isi namaBarang", namaBarang);
                //perhitungannya nimpa yang ini, cuma bisa yang mugg(paling bawah)(solved)
                break;

            case R.id.value_jml:
                Log.d("isi jml", String.valueOf(jumlahBarang));
                hargaBarang.setdJumlah(jumlahBarang);
                if (namaBarang.equalsIgnoreCase("Gantungan Kunci")) {
                    hitungHargaGantunganKunci();
                } else if (namaBarang.equalsIgnoreCase("Pin")) {
                    hitungHargaPin();
                } else if (namaBarang.equalsIgnoreCase("Kaos Satuan")) {
                    hitungHargaKaosSatuan();
                } else if (namaBarang.equalsIgnoreCase("Kaos Standard")) {
                    hitungHargaKaosStandard();
                } else if (namaBarang.equalsIgnoreCase("Kaos Ekonomis")) {
                    hitungHargaKaosEkonomis();
                }
                break;
            case R.id.value_panjang:
                hargaBarang.setdPanjang(jumlahBarang);
                if (namaBarang.equalsIgnoreCase("Spanduk")) {
                    hitungHargaSpanduk();
                } else if (namaBarang.equalsIgnoreCase("Baliho")) {
                    hitungHargaBaliho();
                } else if (namaBarang.equalsIgnoreCase("Kain")) {
                    hitungHargaKain();
                } else if (namaBarang.equalsIgnoreCase("Bendera")) {
                    hitungHargaBendera();
                } else if (namaBarang.equalsIgnoreCase("Sticker")) {
                    hitungHargaSticker();
                }
                Log.d("isi namaBarang", namaBarang);
                break;
            case R.id.value_lebar:
                hargaBarang.setdLebar(jumlahBarang);
                if (namaBarang.equalsIgnoreCase("Spanduk")) {
                    hitungHargaSpanduk();
                } else if (namaBarang.equalsIgnoreCase("Baliho")) {
                    hitungHargaBaliho();
                } else if (namaBarang.equalsIgnoreCase("Kain")) {
                    hitungHargaKain();
                } else if (namaBarang.equalsIgnoreCase("Bendera")) {
                    hitungHargaBendera();
                } else if (namaBarang.equalsIgnoreCase("Sticker")) {
                    hitungHargaSticker();
                    Log.d("isi namaBarang", namaBarang);
                }
                break;
            case R.id.value_jml_halaman:
                hargaBarang.setdJmlHalaman(jumlahBarang);
                if (namaBarang.equalsIgnoreCase("Flyer Ekonomis")) {
                    hitungHargaFlyerEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Brosur Ekonomis")) {
                    hitungHargaBrosurEkonomis();
                } else if (namaBarang.equalsIgnoreCase("Booklet")) {
                    hitungHargaBooklet();
                } else if (namaBarang.equalsIgnoreCase("Katalog")) {
                    hitungHargaKatalog();
                } else if (namaBarang.equalsIgnoreCase("Brosur Hitam Putih")) {
                    hitungHargaBrosurHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Flyer Hitam Putih")) {
                    hitungHargaFlyerHitamPutih();
                } else if (namaBarang.equalsIgnoreCase("Brosur")) {
                    hitungHargaBrosur();
                } else if (namaBarang.equalsIgnoreCase("Flyer Standard")) {
                    hitungHargaFlyerStandard();
                } else if (namaBarang.equalsIgnoreCase("Stempel")) {
                    hitungHargaStempel();
                } else if (namaBarang.equalsIgnoreCase("Cutting Sticker")) {
                    hitungHargaCuttingSticker();
                } else if (namaBarang.equalsIgnoreCase("Laporan")) {
                    hitungHargaLaporan();
                } else if (namaBarang.equalsIgnoreCase("Buku")) {
                    hitungHargaBuku();
                } else if (namaBarang.equalsIgnoreCase("Berkas")) {
                    hitungHargaBerkas();
                } else if (namaBarang.equalsIgnoreCase("Portofolio")) {
                    hitungHargaPortofolio();
                } else if (namaBarang.equalsIgnoreCase("Skripsi")) {
                    hitungHargaSkripsi();
                } else if (namaBarang.equalsIgnoreCase("Buku Lipat")) {
                    hitungHargaBukuLipat();
                }
                break;
            case R.id.value_baju_xs:
                hargaBarang.setdBajuXs(jumlahBarang);
                hitungHargaKaosSatuan();
                break;
            case R.id.value_baju_s:
                hargaBarang.setdBajuS(jumlahBarang);
                hitungHargaKaosSatuan();
                break;
            case R.id.value_baju_m:
                hargaBarang.setdBajuM(jumlahBarang);
                hitungHargaKaosSatuan();
                break;
            case R.id.value_baju_xl:
                hargaBarang.setDbajuXl(jumlahBarang);
                hitungHargaKaosSatuan();
            case R.id.value_baju_xxl:
                hargaBarang.setdBajuXxl(jumlahBarang);
                hitungHargaKaosSatuan();
                break;
        }

    }


}
