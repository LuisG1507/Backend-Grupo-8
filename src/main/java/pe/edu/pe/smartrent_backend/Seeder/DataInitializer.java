package pe.edu.pe.smartrent_backend.Seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.pe.smartrent_backend.Entities.*;
import pe.edu.pe.smartrent_backend.Repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository usersRepository;
    private final IEstateRepository estateRepository;
    private final IContractRepository contractRepository;
    private final INotificationsRepository notificationsRepository;
    private final IConversationRepository conversationRepository;
    private final IMessagesRepository messagesRepository;
    private final IFavoriteRepository favoriteRepository;
    private final IReviewsRepository reviewsRepository;
    private final IModels3DRepository models3DRepository;
    private final IRiskPointsRepository riskPointsRepository;
    private final IRiskReportRepository riskReportRepository;
    private final IUserBackgroundRepository userBackgroundRepository;

    public DataInitializer(IUserRepository usersRepository,
                           IEstateRepository estateRepository,
                           IContractRepository contractRepository,
                           INotificationsRepository notificationsRepository,
                           IConversationRepository conversationRepository,
                           IMessagesRepository messagesRepository,
                           IFavoriteRepository favoriteRepository,
                           IReviewsRepository reviewsRepository,
                           IModels3DRepository models3DRepository,
                           IRiskPointsRepository riskPointsRepository,
                           IRiskReportRepository riskReportRepository,
                           IUserBackgroundRepository userBackgroundRepository) {

        this.usersRepository = usersRepository;
        this.estateRepository = estateRepository;
        this.contractRepository = contractRepository;
        this.notificationsRepository = notificationsRepository;
        this.conversationRepository = conversationRepository;
        this.messagesRepository = messagesRepository;
        this.favoriteRepository = favoriteRepository;
        this.reviewsRepository = reviewsRepository;
        this.models3DRepository = models3DRepository;
        this.riskPointsRepository = riskPointsRepository;
        this.riskReportRepository = riskReportRepository;
        this.userBackgroundRepository = userBackgroundRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(usersRepository.count() > 0){
            return;
        }

        Random random = new Random();

        List<User> listaUsuarios = new ArrayList<>();
        List<Estate> listaEstate = new ArrayList<>();
        List<Conversation> listaConversaciones = new ArrayList<>();
        List<Models3D> listaModelos = new ArrayList<>();

        // ================= USERS + ROLE + BACKGROUND + NOTIFICATIONS =================
        for(int i = 1; i <= 20; i++) {

            User user = new User();
            List<Role> rolesList = new ArrayList<>();
            Role role = new Role();

            user.setDni(70000000 + i);
            user.setPassword("$2a$12$0A8Ho777.btQTmhTXdZZRuJlnSzsoV3expZ3HikyP/gQFHghn2Tc2");
            user.setStatusVerification(random.nextBoolean());
            user.setProfilePhoto("https://foto.com/user" + i + ".jpg");
            user.setPhoneNumber("99988877" + i);
            user.setCreatedDate(LocalDate.now().minusDays(random.nextInt(120)));
            user.setUpdateDate(LocalDate.now());
            user.setEnabled(true);

            // Lógica Exclusiva para el ADMIN
            if (i == 1) {
                user.setName("Admin");
                user.setLastName("System");
                user.setUsername("admin");

                role.setRol("ADMIN");
                role.setUser(user);
                rolesList.add(role);
                user.setRoles(rolesList);

                // Gracias a CascadeType.ALL en User, esto guarda también el Rol
                usersRepository.save(user);

                // Omitimos que el admin se agregue a la lista de usuarios para operaciones
                // de negocio (inmuebles, reportes, contratos, etc.)
                continue;
            }

            // Lógica para USUARIOS NORMALES (ARRENDADORES / ARRENDATARIOS)
            user.setName("Usuario" + i);
            user.setLastName("Apellido" + i);
            user.setUsername("user" + i);

            role.setRol(i % 2 == 0 ? "ARRENDADOR" : "ARRENDATARIO");
            role.setUser(user);
            rolesList.add(role);
            user.setRoles(rolesList);

            usersRepository.save(user);

            // Agregamos a la lista solo a los usuarios normales
            listaUsuarios.add(user);

            // Background y Notificaciones solo para usuarios normales
            UsersBackground ub = new UsersBackground();
            ub.setType(i % 2 == 0 ? "HISTORIAL_POSITIVO" : "INCIDENCIA_MENOR");
            ub.setDescription("Antecedente automático del usuario " + i);
            ub.setSource("SISTEMA");
            ub.setRegistrationDate(LocalDate.now().minusDays(random.nextInt(90)));
            ub.setUser(user);
            userBackgroundRepository.save(ub);

            Notifications n = new Notifications();
            n.setTitle("Notificación " + i);
            n.setMessage("Mensaje automático generado para usuario " + i);
            n.setType(i % 3 == 0 ? "SEGURIDAD" : (i % 2 == 0 ? "PAGO" : "SISTEMA"));
            n.setRead(random.nextBoolean());
            n.setCreatedDate(LocalDate.now().minusDays(random.nextInt(20)));
            n.setUser(user);
            notificationsRepository.save(n);
        }

        // ================= ESTATE + MODELS3D =================
        for(int i = 1; i <= 20; i++) {

            Estate estate = new Estate();
            estate.setTitle("Departamento " + i);
            estate.setDescription("Hermoso inmueble en alquiler número " + i);
            estate.setAdress("Av Principal " + i);
            estate.setDistrict(i % 2 == 0 ? "Villa El Salvador" : "San Juan de Miraflores");
            estate.setCity("Lima");
            estate.setMonthlyPrice(900.0 + random.nextInt(2500));
            estate.setType(i % 2 == 0 ? "Departamento" : "Casa");
            estate.setState(true);
            estate.setRooms(1 + random.nextInt(5));
            estate.setBathrooms(1 + random.nextInt(3));
            estate.setAreaM2(60.0 + random.nextInt(150));
            estate.setCreationDate(LocalDate.now().minusDays(random.nextInt(100)));

            // Selecciona un usuario al azar que NO es admin
            User propietario = listaUsuarios.get(random.nextInt(listaUsuarios.size()));
            estate.setUsers(propietario);

            estateRepository.save(estate);
            listaEstate.add(estate);

            Models3D model = new Models3D();
            model.setFileURL("https://files.com/modelo" + i + ".glb");
            model.setState("ACTIVO");
            model.setCreateDate(LocalDate.now().minusDays(random.nextInt(60)));
            model.setEstate(estate);
            models3DRepository.save(model);
            listaModelos.add(model);
        }

        // ================= RISK POINTS =================
        for(int i = 1; i <= 30; i++) {
            RiskPoints rp = new RiskPoints();
            rp.setDescription("Punto de riesgo detectado " + i);
            rp.setCordX(random.nextDouble() * 10);
            rp.setCordY(random.nextDouble() * 10);
            rp.setCordZ(random.nextDouble() * 10);
            rp.setSeverity(i % 2 == 0 ? "ALTO" : "MEDIO");
            rp.setModels3D(listaModelos.get(random.nextInt(listaModelos.size())));
            riskPointsRepository.save(rp);
        }

        // ================= CONTRACT =================
        for(int i = 1; i <= 20; i++) {

            Contract contract = new Contract();

            LocalDateTime inicio = LocalDateTime.now().minusDays(random.nextInt(40));
            LocalDateTime fin = inicio.plusMonths(6 + random.nextInt(12));

            Estate inmueble = listaEstate.get(random.nextInt(listaEstate.size()));
            User lessor = inmueble.getUsers();
            User lessee = listaUsuarios.get(random.nextInt(listaUsuarios.size()));

            contract.setStartDate(inicio);
            contract.setEndDate(fin);
            contract.setMonthlyAmount(inmueble.getMonthlyPrice());
            contract.setStatus(random.nextBoolean());
            contract.setCreatedAt(LocalDateTime.now());
            contract.setEstate(inmueble);
            contract.setLessor(lessor);
            contract.setLessee(lessee);

            contractRepository.save(contract);
        }

        // ================= CONVERSATION + MESSAGES =================
        for(int i = 1; i <= 15; i++) {

            Conversation c = new Conversation();

            User u1 = listaUsuarios.get(random.nextInt(listaUsuarios.size()));
            User u2 = listaUsuarios.get(random.nextInt(listaUsuarios.size()));
            Estate e = listaEstate.get(random.nextInt(listaEstate.size()));

            c.setUser1(u1);
            c.setUser2(u2);
            c.setEstate(e);

            conversationRepository.save(c);
            listaConversaciones.add(c);

            for(int j = 1; j <= 3; j++) {
                Messages m = new Messages();
                m.setContent("Hola, estoy interesado en el inmueble " + e.getTitle() + " mensaje " + j);
                m.setStatus(j % 2 == 0 ? "LEIDO" : "ENVIADO");
                m.setDateSent(LocalDate.now().minusDays(random.nextInt(20)));
                m.setConversation(c);
                m.setUser(j % 2 == 0 ? u1 : u2);
                messagesRepository.save(m);
            }
        }

        // ================= FAVORITE =================
        for(int i = 1; i <= 20; i++) {
            Favorite f = new Favorite();
            f.setCreationDate(LocalDate.now().minusDays(random.nextInt(40)));
            f.setUser(listaUsuarios.get(random.nextInt(listaUsuarios.size())));
            f.setEstate(listaEstate.get(random.nextInt(listaEstate.size())));
            favoriteRepository.save(f);
        }

        // ================= REVIEWS =================
        for(int i = 1; i <= 20; i++) {
            Reviews r = new Reviews();
            r.setCalification(1.0 + random.nextInt(5));
            r.setComment("Comentario automático sobre el inmueble " + i);
            r.setCreationDate(LocalDate.now().minusDays(random.nextInt(30)));
            r.setUser(listaUsuarios.get(random.nextInt(listaUsuarios.size())));
            r.setEstate(listaEstate.get(random.nextInt(listaEstate.size())));
            reviewsRepository.save(r);
        }

        // ================= RISK REPORT =================
        for(int i = 1; i <= 15; i++) {
            RiskReport rr = new RiskReport();
            rr.setType("INSPECCION");
            rr.setCreationDate(LocalDate.now().minusDays(random.nextInt(25)));
            rr.setRiskLevel(i % 2 == 0 ? "ALTO" : "MEDIO");
            rr.setDescription("Reporte automático de evaluación " + i);
            rr.setDetails("Detalles técnicos encontrados en la revisión.");
            rr.setUser(listaUsuarios.get(random.nextInt(listaUsuarios.size())));
            rr.setEstate(listaEstate.get(random.nextInt(listaEstate.size())));
            riskReportRepository.save(rr);
        }

        System.out.println("=========== SMART RENT DATA DUMMY INSERTADA CORRECTAMENTE ===========");
    }
}
