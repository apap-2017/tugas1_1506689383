package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga(String nik);

	void tambahKeluarga(KeluargaModel keluarga);
	void ubahKeluarga(KeluargaModel keluarga);
}
