package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.DaerahMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DaerahServiceDatabase implements DaerahService {
	@Autowired
	DaerahMapper daerahMapper;
	
	@Override
	public List<KotaModel> selectAllKota() {
		List<KotaModel> kotas = daerahMapper.selectAllKota();
		
		return kotas;
	}

	@Override
	public List<KecamatanModel> selectKecamatan(String id_kota) {
		List<KecamatanModel> kecamatans = daerahMapper.selectKecamatan(id_kota);
		
		return kecamatans;
	}

	@Override
	public List<KelurahanModel> selectKelurahan(String id_kecamatan) {
		List<KelurahanModel> kelurahans = daerahMapper.selectKelurahan(id_kecamatan);
		return kelurahans;
	}

	@Override
	public KotaModel getKota(String id_kota) {
		return daerahMapper.getKota(id_kota);
	}

	@Override
	public KecamatanModel getKecamatan(String id_kecamatan) {
		return daerahMapper.getKecamatan(id_kecamatan);
	}

	@Override
	public KelurahanModel getKelurahan(String id_kelurahan) {
		return daerahMapper.getKelurahan(id_kelurahan);
	}

}
