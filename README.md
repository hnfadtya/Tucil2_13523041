# Tucil2_13523041
 
<div align="center">

| NIM      | Nama                      |
|----------|---------------------------|
| 13523041 | Hanif Kalyana Aditya      |

</div>

## About

Proyek ini dibuat untuk memenuhi spesifikasi tugas besar mata kuliah Strategi Algoritma


## Deskripsi Program

+ Quadtree adalah struktur data hierarkis yang digunakan untuk membagi ruang atau data
menjadi bagian yang lebih kecil, yang sering digunakan dalam pengolahan gambar. Dalam
konteks kompresi gambar, Quadtree membagi gambar menjadi blok-blok kecil berdasarkan
keseragaman warna atau intensitas piksel. Prosesnya dimulai dengan membagi gambar menjadi
empat bagian, lalu memeriksa apakah setiap bagian memiliki nilai yang seragam berdasarkan
analisis sistem warna RGB, yaitu dengan membandingkan komposisi nilai merah (R), hijau (G),
dan biru (B) pada piksel-piksel di dalamnya. Jika bagian tersebut tidak seragam, maka bagian
tersebut akan terus dibagi hingga mencapai tingkat keseragaman tertentu atau ukuran
minimum yang ditentukan.

## How To Run
Dari *root folder*, ketik `cd bin` untuk pindah ke folder bin. Kemudian ketik <br />
`java main/Main` <br /> pada terminal untuk menjalankan program. <br /> <br />File yang digunakan sebagai *input* dan sebagai tempat menyimpan hasil *output* berada pada folder test

atau 
javac -d bin src/*.java
mkdir bin 
java -cp bin src.Main



```