<!DOCTYPE html>
<!--  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="main-nav-layout sticky">
    <div class="nav_area"><a class="logo box" href=""><img src="img/logo.png" alt=""></a>
    <div class="search">
        <input type="text" value="Search..." />
        <a href="" class="adv-search">Advanced</a>
    </div>
    <div class="main-nav box">
        <ul>
            <li>
                <a href="">penjualan</a>
	            <ul>
	                <li><a href="">nota pesan</a></li>
	                <li><a href="">nota jual</a></li>
	                <li><a href="">surat jalan</a></li>
	                <li><a href="">packing list</a></li>
	            </ul>
            </li>
            <li class="active"><a href=""></i>General</a></li>
            <li><a href="">pembelian</a></li>
            <li><a href=""></i>pengembalian</a></li>
            <li><a href=""></i>stok</a></li>
            <li><a href=""></i>laporan</a></li>
            <li><a href=""></i>kontak</a></li>
            <li><a href=""></i>gudang</a></li>
            <li><a href=""></i>akun pengguna</a></li>
        </ul>
    </div>
    </div>
    <div class="arrow_back"><a href="" class="close_search"><img src="img/arrow-back.png" /></a></div>
</div>
<div class="search-panel">
    <!-- <div class="fl"><a href="" class="close_search"><img src="img/arrow-back.png" /></a></div> -->
    <div class="search_area">
        <a href="" class="fr close_search"><i class="icon-blue icon-close"></i></a>
        <div class="clear"></div>
        <h1>SEARCH</h1>
        <form>
            <div class="field"><input type="text" class="long" value="Masukkan kata kunci ..." /></div> 
            <div class="field">
                <label class="cr-right fl">Kumpulan:</label>
                <div class="field-content fr">
	                <select class="select">
	                    <option>Kumpulan</option>
	                    <option>Item 2</option>
	                    <option>Item 3</option>
	                </select>
	            </div>
                <div class="clear"></div>
            </div>
            <div class="head-filter">FILTER</div>
            <div class="field">
	            <label class="cr-right fl">Periode:</label>
	            <div class="field-content fl">
	                <div class="input-date input-append">
	                    <div class="input-icon"><i class="icon-calendar"></i></div>
	                    <input type="text" class="datepicker" />
	                </div>
	            </div>
	            <div class="field-content fr">
	                <div class="input-date input-append">
	                    <div class="input-icon"><i class="icon-calendar"></i></div>
	                    <input type="text" class="datepicker" />
	                </div>
	            </div>
	            <div class="clear"></div>
            </div>
            <div class="filter-end">
                <a href="" class="btn positive">SEARCH</a>
            </div>
        </form>
    </div>
</div>
