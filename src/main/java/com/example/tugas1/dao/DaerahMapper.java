package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;

@Mapper
public interface DaerahMapper {
	@Select("select kode_kecamatan" + " from keluarga k join kelurahan kl"
			+ " on id_kelurahan = kl.id join kecamatan kc on id_kecamatan = kc.id" + " where k.id=#{id}")
	KecamatanModel selectKode(@Param("id") String id);
	
	@Select("select * from kelurahan")
	List<KelurahanModel> selectAllKelurahan();
	
	@Select("select * from kota")
	List<KotaModel> selectAllKota();
	
	@Select("select * from kecamatan where id_kota=#{id_kota}") 
	List<KecamatanModel> selectKecamatan(String id_kota);
	
	@Select("Select * from kelurahan where id_kecamatan=#{id_kecamatan}")
	List<KelurahanModel> selectKelurahan(String id_kecamatan);
	
	@Select("select * from kota where id=#{id_kota}")
	KotaModel getKota(String id_kota);
	
	@Select("select * from kecamatan where id=#{id_kecamatan}")
	KecamatanModel getKecamatan(String id_kecamatan);
	
	@Select("select nama_kelurahan from kelurahan where id=#{id_kelurahan}")
	KelurahanModel getKelurahan(String id_kelurahan);
}
