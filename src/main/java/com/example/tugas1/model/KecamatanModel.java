package com.example.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.tugas1.model.KotaModel;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	private int id;
	private String nama_kecamatan;
	private String kode_kecamatan;
	private KotaModel kota;
}
