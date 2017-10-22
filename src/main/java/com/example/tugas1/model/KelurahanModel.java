package com.example.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.tugas1.model.KecamatanModel;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	private int id;
	private String kode_kelurahan;
	private String nama_kelurahan;
	private String kode_pos;
	private KecamatanModel kecamatan;
}
