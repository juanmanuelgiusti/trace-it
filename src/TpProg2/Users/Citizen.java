package TpProg2.Users;

import TpProg2.Events.Symptom;
import TpProg2.Exceptions.DataStoreException;
import TpProg2.ImplementOfUsers.*;
import TpProg2.ImplementOfUsers.Zone.Zone;
import TpProg2.Main;
import java.util.ArrayList;

public class Citizen extends User {

    String type;
    boolean isBan;
    boolean seek;
    int rejections;
    ArrayList<Notification> receivedNotifications;
    ArrayList<Notification> notifications;
    ArrayList<Invitation> receivedInvitations; // todas las invitaciones llegan aca. Una vez que se acepta o se rechaza una invitacion se remueve de esta bandeja.
    ArrayList<FaceToFaceMeeting> acceptedRequest; // bandeja de invitaciones aceptadas.
    ArrayList<Citizen> contacts;
    ArrayList<Symptom> registeredSymptoms;
    Date gotSeek;
    Zone zone;

    public Citizen(String userName, String cuil, String phoneNumber) {
        super(userName, cuil, phoneNumber);
        this.receivedNotifications = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.contacts = new ArrayList<>();
        this.receivedInvitations = new ArrayList<>(); // ArrayList<Invitation>
        this.acceptedRequest = new ArrayList<>(); //ArrayList<FaceToFaceMeeting>
        this.isBan = false;
        this.rejections = 0;
        this.type = "Ciudadano";
        this.zone = null;
        this.registeredSymptoms = new ArrayList<>();

    }

    public boolean getSeek(){ return seek;}

    public void setGotSeek(Date gotSeek) {
        this.gotSeek = gotSeek;
    }

    public Date getGotSeek() {
        return gotSeek;
    }

    public ArrayList<Citizen> getContacts() {
        return contacts;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public ArrayList<Notification> getReceivedNotifications() {
        return receivedNotifications;
    }

    public ArrayList<Invitation> getReceivedInvitations() {
        return receivedInvitations;
    }

    public ArrayList<FaceToFaceMeeting> getAcceptedRequest() {
        return acceptedRequest;
    }

    public ArrayList<Symptom> getRegisteredSymptoms() {
        return registeredSymptoms;
    }

    public Zone getZone() {
        return this.zone;
    }

    public void setRejections(int rejections) {
        this.rejections = rejections;
    }

    public boolean isBan() {
        return isBan;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setSeek(boolean seek) {
        this.seek = seek;
    }

    public void setReceivedNotifications(ArrayList<Notification> receivedNotifications) {
        this.receivedNotifications = receivedNotifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setReceivedInvitations(ArrayList<Invitation> receivedInvitations) {
        this.receivedInvitations = receivedInvitations;
    }

    public void setAcceptedRequest(ArrayList<FaceToFaceMeeting> acceptedRequest) {
        this.acceptedRequest = acceptedRequest;
    }

    public void setRegisteredSymptoms(ArrayList<Symptom> registeredSymptoms) {
        this.registeredSymptoms = registeredSymptoms;
    }

    public void setContacts(ArrayList<Citizen> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String getFileRepresentation() {
        String gotSeekRepresentation = "no date";
        if (this.gotSeek != null){
            gotSeekRepresentation = this.gotSeek.getFileRepresentation();
        }
        return super.getFileRepresentation() + "," + isBan + "," + rejections + "," + zone.getName() + "," + seek + "," + gotSeekRepresentation;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getCuil() {
        return super.getCuil();
    }

    @Override
    public String getUserName() {
        return super.getUserName();
    }

    @Override
    public String getType() {
        return this.type + "s";
    }

    // Meetings
    public void receiveMeetingRequest(Invitation invitation){ // aceptar o rechazar invitacion para faceToFaceMeeting; - Nacho B
        receivedInvitations.add(invitation);
    } // Recibe una invitacion dentro del inbox/bandeja de entrada

    public void sendRequest(Citizen sendTo, Invitation invitation){
        sendTo.receiveMeetingRequest(invitation);
    }

    public void acceptedRequest(Invitation invitation) throws DataStoreException {
        receivedInvitations.remove(invitation);
        acceptedRequest.add(invitation.meeting);
        this.addContact(invitation.transmitter);
        invitation.transmitter.addContact(this);
        if (seek && Date.howLongAgo(gotSeek) < 47 && Date.howLongAgo(gotSeek) > 0){
            sendNotification(invitation.transmitter, new Notification(this, this.getGotSeek()));
        }
    } // Metodo que acepta una invitacion dentro de su bandeja de entrada y guarda registro de este encuentro/meeting.

    public void rejectedRequest(Invitation invitation){
        receivedInvitations.remove(invitation);
        invitation.transmitter.rejections ++;
        if (invitation.transmitter.rejections >= 5){
            invitation.transmitter.isBan  = true;
            Main.generalAMB.getBannedCitizens().add(invitation.transmitter);
        }
    } // Metodo que rechaza una invitacion dentro de su bandeja de entrada y se suma a la cuenta de rechazos del emisor.

    //Notifications
    public void receiveNotification(Notification notification){
        if (contacts.contains(notification.seekCitizen)){
            notifications.add(notification);
        }else{
            receivedNotifications.add(notification);
        }
    } // Recibe una notificacion, si el emisor de la misma es parte de nuestros contactos, se agregara a las notificaciones visibles.

    public void sendNotification(Citizen sendTo, Notification notification){
        boolean avisar = true;
        for (int j = 0; j < sendTo.getReceivedNotifications().size(); j++) {// revisamos que ya no tenga ninguna notificacion nuestra en las notificaciones no visibles
            if (sendTo.getReceivedNotifications().get(j).seekCitizen.equals(this)) {
                avisar = false;
            }
        }
        for (int j = 0; j < sendTo.getNotifications().size(); j++) {// revisamos que ya no tenga ninguna notificacion nuestra en las notificaciones visibles
            if (sendTo.getNotifications().get(j).seekCitizen.equals(this)) {
                avisar = false;
            }
        }
        if (avisar){
            sendTo.receiveNotification(notification);
            sendTo.refreshNotifications();
        }
    } // Envia una notificacion a un ciudadano, esta no debe estar repetida.

    public void addContact(Citizen contact){
        if (contacts == null || !contacts.contains(contact)){
            contacts.add(contact);
        }
        refreshNotifications();// Es posible tener una notificacion de esta persona antes de tenerla como contacto, por eso se actualiza la lista despues de "agregar a alguien"
    } // Agrega un ciudadano a la lista de contactos.

    public void refreshNotifications(){
        for (int i = 0; i < receivedNotifications.size(); i++) {
            if (contacts.contains(receivedNotifications.get(i).seekCitizen)){
                notifications.add(receivedNotifications.get(i));
                receivedNotifications.remove(receivedNotifications.get(i));
            }
        }
    } // Actualiza las notificaciones (hace visibles las notificaciones de nuevos contactos)

    //Sintomas
    public boolean isSeek() throws DataStoreException {
        int count = 0;
        ArrayList<String> symptoms = Main.generalAMB.getSymptomsStrings();
        ArrayList<String> registeredSymptoms = getRegisteredSymptomsNames();

        for (int i = 0; i < registeredSymptoms.size(); i++) {
            if (symptoms.contains(registeredSymptoms.get(i))){
                count ++;
            }
        }
        if (count >= 3) {
            gotSeek = Date.actualDate();
            Main.generalAMB.addSeekCitizen(this);
            seek = true;
            return true;
        } else{
            Main.generalAMB.removeSeekCitizen(this);
            seek = false;
            return false;
        }
    } // Confirma si un ciudadano tiene suficientes sintomas como para considerarse enfermo

    public ArrayList<String> getRegisteredSymptomsNames(){
        ArrayList<String> symptomsNames = new ArrayList<>();
        for (int i = 0; i < getRegisteredSymptoms().size(); i++) {
            symptomsNames.add(getRegisteredSymptoms().get(i).getName());
        }
        return symptomsNames;
    } // Devuelve un arrayList de los nombres de los sintomas registrados.
}
