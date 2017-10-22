package com.example.tugas1.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;

	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		return keluargaMapper.selectKeluarga(nkk);
	}

	@Override
	public void tambahKeluarga(KeluargaModel keluarga) {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		String datenow = dateFormat.format(date);
		
		keluarga.setIs_tidak_berlaku("0");
		String nkk = this.validasiNkk(keluarga, datenow);
		keluarga.setNomor_kk(nkk);

		keluargaMapper.tambahKeluarga(keluarga);
	}

	@Override
	public void ubahKeluarga(KeluargaModel keluarga) {
		String nkkLama = keluarga.getNomor_kk();
		KeluargaModel kelLama = keluargaMapper.selectKeluarga(nkkLama);
		log.info(nkkLama);
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		String dateNow = dateFormat.format(date);
		
		if(!(kelLama.getNomor_kk().substring(6, 12).equals(dateNow)) || keluarga.getId_kelurahan() != keluarga.getNama_kecamatan()) {
			String nkk = this.validasiNkk(keluarga, dateNow);
			keluarga.setNomor_kk(nkk);
		}
		
		KelurahanModel kel = keluargaMapper.getIdKel(keluarga.getNama_kelurahan());
		String idKel = "" + kel.getId();
		keluarga.setId_kelurahan(idKel);
		
		keluargaMapper.ubahKeluarga(keluarga.getNomor_kk(), keluarga.getAlamat(), keluarga.getRt(), keluarga.getRw(), keluarga.getId_kelurahan(), nkkLama);
	}

	public String validasiNkk(KeluargaModel keluarga, String dateNow) {
		
		// String namaKel = "lcase('" + keluarga.getNama_kelurahan() + "')";
		KelurahanModel kelurahan = keluargaMapper.getIdKel(keluarga.getNama_kelurahan());
		KecamatanModel kecamatan = keluargaMapper.getIdKec(keluarga.getNama_kecamatan());
		// KotaModel kota = keluargaMapper.getIdKota(keluarga.getNama_kota());

		String idKel = "" + kelurahan.getId();
		
		keluarga.setId_kelurahan(idKel);

		String nkk = kecamatan.getKode_kecamatan().substring(0, 6);
		log.info(nkk);
		int kode = 1;

		String cek = nkk + dateNow + "%";
		int add = 0;
		List<KeluargaModel> a = keluargaMapper.selectNKK(cek);
		String tes = a.size() + "";
		
		log.info(tes);
		
		for (int i = 0; i < a.size(); i++) {
			String last = a.get(i).getNomor_kk().substring(12, 16);
			add = Integer.parseInt(last);
		}
		
		kode = kode + add;
		String akhir = "";
		if (kode < 10) {
			akhir = "000" + kode;
		} else if (kode >= 10 && kode <= 99) {
			akhir = "00" + kode;
		} else if (kode > 99 && kode <= 999) {
			akhir = "0" + kode;
		} else {
			akhir = "" + kode;
		}

		nkk = nkk + dateNow + akhir;
		return nkk;
	}



}
