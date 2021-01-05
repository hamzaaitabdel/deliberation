package com.springboot.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DelibirationSemestre {
    private String cne;
    private String semestre;
    private boolean valid;
    public DelibirationSemestre(String cne,String s,boolean v){
        this.cne=cne;
        this.semestre=s;
        this.valid=v;
    }
}
