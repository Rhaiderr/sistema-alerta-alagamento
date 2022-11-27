package sistema_alagamento;

public class Data {

    private String data;
    private String hora;
    private String mensagem;

    public  Data(String data, String hora, String mensagem){
        this.data  = data;
        this.hora = hora;
        this.mensagem = mensagem;
    }

    public String getData(){ return this.data; }
    public String getHora(){ return this.hora; }
    public String getMensagem(){ return this.mensagem; }


}
