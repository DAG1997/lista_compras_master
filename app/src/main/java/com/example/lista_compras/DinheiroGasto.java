package com.example.lista_compras;

import android.content.ContentValues;
import android.database.Cursor;

    public class DinheiroGasto {
        private long id;
        private String dia;
        private int montante_gasto;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            this.dia = dia;
        }

        public int getMontante_gasto() {
            return montante_gasto;
        }

        public void setMontante_gasto(int montante_gasto) {
            this.montante_gasto = montante_gasto;
        }

        public ContentValues getContentValues(){
            ContentValues valores = new ContentValues();

            valores.put(BdTableDinheiroGasto.DIA, dia);
            valores.put(BdTableDinheiroGasto.MONTANTE_GASTO, montante_gasto);


            return valores;
        }

        public static DinheiroGasto fromCursor(Cursor cursor) {
            long id = cursor.getLong(
                    cursor.getColumnIndex(BdTableDinheiroGasto._ID)
            );

            String dia = cursor.getString(
                    cursor.getColumnIndex(BdTableDinheiroGasto.DIA)
            );

             int montante_gasto = cursor.getInt(
                    cursor.getColumnIndex(BdTableDinheiroGasto.MONTANTE_GASTO)
            );






            DinheiroGasto dinheiroGasto = new DinheiroGasto();

            dinheiroGasto.setId(id);
            dinheiroGasto.setDia(dia);
            dinheiroGasto.setMontante_gasto(montante_gasto);


            return dinheiroGasto;
        }

    }


