package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	boolean tambahPenduduk(PendudukModel penduduk);
	boolean ubahPenduduk(PendudukModel penduduk);
	void nonaktifPenduduk(PendudukModel penduduk);
	List<PendudukModel> pendudukKelurahan (String id_kelurahan);
}
