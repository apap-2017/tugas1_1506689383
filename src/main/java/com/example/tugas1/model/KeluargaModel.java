package com.example.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.tugas1.model.PendudukModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private int id;
	private String nomor_kk;
	private String alamat;
	private String rt;
	private String rw;
	private String id_kelurahan;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
	private String is_tidak_berlaku;
	private List<PendudukModel> penduduks;
}
