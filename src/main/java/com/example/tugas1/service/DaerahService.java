package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;

public interface DaerahService {
	List<KotaModel> selectAllKota();
	List<KecamatanModel> selectKecamatan(String id_kota);
	List<KelurahanModel> selectKelurahan(String id_kecamatan);
	KotaModel getKota(String id_kota);
	KecamatanModel getKecamatan(String id_kecamatan);
	KelurahanModel getKelurahan(String id_kelurahan);
}
