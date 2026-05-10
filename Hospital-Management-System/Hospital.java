import java.util.Scanner;

public class Hospital {

    static Scanner sc = new Scanner(System.in);

    // ── 6 Doctors ──
    // Category 0 = Fever/Cough, 1 = ENT, 2 = Other
    static String[] doctorName     = {"Dr. Arjun",   "Dr. Priya",   "Dr. Ramesh",  "Dr. Kavitha", "Dr. Suresh",  "Dr. Meena"};
    static int[]    doctorCategory = {0,              0,              1,              1,              2,              2};
    static boolean[]doctorFree     = {true,           true,           true,           true,           true,           true};
    static int[]    doctorFee      = {500,            500,            600,            600,            700,            700};

    // ── Patient list ──
    static String[] patientName      = new String[100];
    static int[]    patientAge       = new int[100];
    static String[] patientDisease   = new String[100];
    static int[]    patientDays      = new int[100];
    static boolean[]patientEmergency = new boolean[100]; // true = admitted, false = outpatient
    static int[]    assignedDoc      = new int[100];     // -1 = no doctor assigned
    static int      count            = 0;

    // ──────────────────────────────────────────
    public static void main(String[] args) {
        int choice;
        System.out.println("=== Hospital Management System ===");

        do {
            System.out.println("\n1. Add Patient & Assign Doctor");
            System.out.println("2. View All Patients");
            System.out.println("3. Generate Bill & Discharge Patient");
            System.out.println("4. View Doctor Status");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt(); sc.nextLine();

            if      (choice == 1) addPatient();
            else if (choice == 2) viewPatients();
            else if (choice == 3) generateBillAndDischarge();
            else if (choice == 4) viewDoctors();
            else if (choice == 0) System.out.println("Goodbye!");
            else System.out.println("Invalid choice.");

        } while (choice != 0);
    }

    // ── 1. Add Patient & Auto-Assign Doctor ──
    static void addPatient() {
        System.out.println("\n-- Add Patient --");
        System.out.print("Name    : "); patientName[count] = sc.nextLine();
        System.out.print("Age     : "); patientAge[count]  = sc.nextInt(); sc.nextLine();

        System.out.println("Disease : 1.Fever/Cough  2.ENT  3.Other");
        System.out.print("Choose  : ");
        int cat = sc.nextInt() - 1; sc.nextLine();

        // Emergency or Outpatient
        System.out.print("Is Emergency? (1=Yes / 2=No): ");
        int emg = sc.nextInt(); sc.nextLine();
        patientEmergency[count] = (emg == 1);

        // Ask days only if admitted (emergency)
        if (patientEmergency[count]) {
            System.out.print("Days to Admit : ");
            patientDays[count] = sc.nextInt(); sc.nextLine();
        } else {
            patientDays[count] = 0; // outpatient -- no days
        }

        String[] catNames = {"Fever/Cough", "ENT", "Other"};
        patientDisease[count] = catNames[cat];

        // Auto-assign a free doctor of that category
        int doc = findFreeDoctor(cat);

        if (doc == -1) {
            System.out.println("No free doctor available for " + catNames[cat] + " right now!");
            assignedDoc[count] = -1;
        } else {
            assignedDoc[count] = doc;
            doctorFree[doc]    = false;
            System.out.println("Assigned Doctor : " + doctorName[doc]);
            if (patientEmergency[count])
                System.out.println("Status          : Admitted to Hospital");
            else
                System.out.println("Status          : Outpatient (Home after consultation)");
        }
        count++;
    }

    // ── Find a free doctor by category ──
    static int findFreeDoctor(int category) {
        for (int i = 0; i < 6; i++) {
            if (doctorCategory[i] == category && doctorFree[i]) return i;
        }
        return -1;
    }

    // ── 2. View All Patients ──
    static void viewPatients() {
        if (count == 0) { System.out.println("No patients yet."); return; }
        System.out.println("\n-- Patient List --");
        System.out.printf("%-5s %-15s %-5s %-14s %-12s %-5s %-15s%n",
                "No.", "Name", "Age", "Disease", "Type", "Days", "Doctor");
        System.out.println("--------------------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            String doc  = (assignedDoc[i] == -1) ? "Not Assigned" : doctorName[assignedDoc[i]];
            String type = patientEmergency[i] ? "Emergency" : "Outpatient";
            System.out.printf("%-5d %-15s %-5d %-14s %-12s %-5d %-15s%n",
                    i + 1, patientName[i], patientAge[i],
                    patientDisease[i], type, patientDays[i], doc);
        }
    }

    // ── 3. Generate Bill & Discharge ──
    static void generateBillAndDischarge() {
        viewPatients();
        if (count == 0) return;

        System.out.print("\nEnter Patient No. to discharge: ");
        int no = sc.nextInt() - 1; sc.nextLine();

        if (no < 0 || no >= count) { System.out.println("Invalid number."); return; }
        if (assignedDoc[no] == -1) { System.out.println("No doctor assigned."); return; }

        int doc       = assignedDoc[no];
        int days      = patientDays[no];
        boolean emergency = patientEmergency[no];

        // ── Bill calculation ──
        int room   = emergency ? days * 500 : 0;           // Room only if admitted
        int docFee = emergency ? days * doctorFee[doc]     // Per day if admitted
                               : doctorFee[doc];            // Single visit if outpatient
        int med    = emergency ? days * 200                 // Per day if admitted
                               : 200;                       // Fixed Rs.200 if outpatient
        int total  = room + docFee + med;

        // ── Print Bill ──
        System.out.println("\n====== BILL RECEIPT ======");
        System.out.println("Patient  : " + patientName[no]);
        System.out.println("Disease  : " + patientDisease[no]);
        System.out.println("Doctor   : " + doctorName[doc]);
        System.out.println("Type     : " + (emergency ? "Emergency (Admitted)" : "Outpatient (Consultation)"));
        if (emergency)
            System.out.println("Days     : " + days);
        System.out.println("--------------------------");

        if (emergency)
            System.out.println("Room     : Rs." + room + "  (Rs.500 x " + days + " days)");
        else
            System.out.println("Room     : Rs.0  (Not admitted)");

        if (emergency)
            System.out.println("Doctor   : Rs." + docFee + "  (Rs." + doctorFee[doc] + " x " + days + " days)");
        else
            System.out.println("Doctor   : Rs." + docFee + "  (Single consultation)");

        if (emergency)
            System.out.println("Medicine : Rs." + med + "  (Rs.200 x " + days + " days)");
        else
            System.out.println("Medicine : Rs." + med + "  (Fixed for outpatient)");

        System.out.println("--------------------------");
        System.out.println("TOTAL    : Rs." + total);
        System.out.println("==========================");

        // Deassign doctor -- doctor is free again
        doctorFree[doc] = true;
        System.out.println(doctorName[doc] + " is now free for next patient.");

        // Remove patient from list
        removePatient(no);
        System.out.println("Patient discharged successfully!");
    }

    // ── Remove patient from array ──
    static void removePatient(int index) {
        for (int i = index; i < count - 1; i++) {
            patientName[i]      = patientName[i + 1];
            patientAge[i]       = patientAge[i + 1];
            patientDisease[i]   = patientDisease[i + 1];
            patientDays[i]      = patientDays[i + 1];
            patientEmergency[i] = patientEmergency[i + 1];
            assignedDoc[i]      = assignedDoc[i + 1];
        }
        count--;
    }

    // ── 4. View Doctor Status ──
    static void viewDoctors() {
        System.out.println("\n-- Doctor Status --");
        String[] catName = {"Fever/Cough", "ENT", "Other"};
        System.out.printf("%-15s %-14s %-10s %-10s%n", "Doctor", "Category", "Fee/Day", "Status");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < 6; i++) {
            System.out.printf("%-15s %-14s Rs.%-7d %-10s%n",
                    doctorName[i], catName[doctorCategory[i]],
                    doctorFee[i], doctorFree[i] ? "FREE" : "BUSY");
        }
    }
}