package TpProg2.Users;

        import TpProg2.DataStore.DataStore;
        import TpProg2.Exceptions.ABMAdminException;
        import TpProg2.Exceptions.ABMAdminException2;
        import TpProg2.Exceptions.ABMUserException;

public class ABMAdmin implements ABM<Administrator>/*metodos que va a utilizar ABM*/{

    DataStore<Administrator> dataStore;

    public ABMAdmin(DataStore<Administrator> dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Administrator add(Administrator administrator) throws ABMAdminException, ABMUserException {
        if (this.dataStore.findById(administrator.getId()) == null){
            this.dataStore.save(administrator);
            return administrator;
        }
        throw new ABMAdminException(administrator.getId());

    }

    @Override
    public void remove(Administrator administrator) throws ABMAdminException2, ABMUserException {
        if (this.dataStore.findById(administrator.getId()) != null){
            this.dataStore.remove(administrator.getId());
        }
        throw new ABMAdminException2(administrator.getId());
    }

    @Override
    public void edit(Administrator administrator) throws ABMAdminException2, ABMUserException {
        if (this.dataStore.findById(administrator.getId()) != null){
            this.dataStore.edit(administrator);
        }
        throw new ABMAdminException2(administrator.getId());
    }

}
