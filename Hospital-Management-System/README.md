# Hospital Management System 🏥

A comprehensive Java-based hospital management system designed to efficiently manage patients, doctors, and billing operations.

## 📋 Features

### 1. **Patient Management**
   - Add new patients with personal details (name, age, disease)
   - Distinguish between Emergency (admitted) and Outpatient cases
   - Automatic doctor assignment based on disease category
   - Support for up to 100 concurrent patients

### 2. **Doctor Management**
   - 6 specialized doctors across 3 categories:
     - **Fever/Cough**: Dr. Arjun, Dr. Priya
     - **ENT**: Dr. Ramesh, Dr. Kavitha
     - **Other Specialties**: Dr. Suresh, Dr. Meena
   - Real-time doctor availability tracking
   - Category-based automatic assignment

### 3. **Billing System**
   - Differential billing for Emergency vs. Outpatient cases
   - Detailed bill generation with itemized breakdown
   - Components: Room charges, Doctor fees, Medicine costs

### 4. **Patient Discharge**
   - Generate comprehensive bill receipt
   - Automatic doctor release for next patient
   - Patient removal from active list

## 🏗️ System Architecture

### Data Structures

**Doctor Management:**
```
doctorName[6]     - Names of 6 doctors
doctorCategory[6] - Specialization (0=Fever/Cough, 1=ENT, 2=Other)
doctorFree[6]     - Availability status (true=free, false=busy)
doctorFee[6]      - Consultation fee per day (Rs.)
```

**Patient Management:**
```
patientName[100]      - Patient names (max 100)
patientAge[100]       - Patient ages
patientDisease[100]   - Disease category
patientDays[100]      - Duration of admission (0 for outpatient)
patientEmergency[100] - Admission type (true=emergency, false=outpatient)
assignedDoc[100]      - Assigned doctor index (-1 if unassigned)
count                 - Current patient count
```

## 💰 Billing Formula

### Emergency (Admitted) Patient
```
Room Cost    = Days × Rs.500
Doctor Fee   = Days × Doctor Fee
Medicine     = Days × Rs.200
─────────────────────────────
Total Bill   = Room + Doctor Fee + Medicine
```

### Outpatient (Consultation)
```
Room Cost    = Rs.0
Doctor Fee   = Single consultation fee
Medicine     = Rs.200 (fixed)
─────────────────────────────
Total Bill   = Doctor Fee + Rs.200
```

## 🚀 Usage Instructions

### Compilation
```bash
javac Hospital.java
```

### Execution
```bash
java Hospital
```

### Main Menu Options

**1. Add Patient & Assign Doctor**
   - Input patient details (name, age)
   - Select disease category
   - Choose admission type (Emergency/Outpatient)
   - System auto-assigns available doctor

**2. View All Patients**
   - Displays formatted table of all active patients
   - Shows: Name, Age, Disease, Type, Days, Assigned Doctor

**3. Generate Bill & Discharge Patient**
   - View current patients
   - Select patient for discharge
   - Generates itemized bill receipt
   - Releases doctor for next appointment

**4. View Doctor Status**
   - Shows all doctors
   - Displays specialization, fee, and current status

**0. Exit**
   - Cleanly closes the application

## 📊 Example Workflow

```
Step 1: Add Patient
- Name: Raj Kumar
- Age: 35
- Disease: Fever/Cough (Category 1)
- Emergency: Yes
- Days: 3
→ Dr. Arjun assigned

Step 2: View Patients
- Raj Kumar (35) | Fever/Cough | Emergency | 3 days | Dr. Arjun

Step 3: Discharge & Bill Generation
- Patient: Raj Kumar
- Bill Breakdown:
  ├─ Room: Rs.1500 (500 × 3 days)
  ├─ Doctor: Rs.1500 (500 × 3 days)
  ├─ Medicine: Rs.600 (200 × 3 days)
  └─ TOTAL: Rs.3600
→ Dr. Arjun is now FREE
```

## 🔧 Technical Details

| Property | Value |
|----------|-------|
| Language | Java |
| Max Patients | 100 |
| Doctors | 6 |
| Categories | 3 |
| Input Method | Command Line (Scanner) |
| Data Storage | Static Arrays |

## 📈 Doctor Fee Structure

| Category | Doctors | Fee/Day |
|----------|---------|---------|
| Fever/Cough | Dr. Arjun, Dr. Priya | Rs.500 |
| ENT | Dr. Ramesh, Dr. Kavitha | Rs.600 |
| Other | Dr. Suresh, Dr. Meena | Rs.700 |

## 🎯 Key Functions

| Function | Purpose |
|----------|---------|
| `addPatient()` | Register new patient and assign doctor |
| `viewPatients()` | Display all active patients |
| `generateBillAndDischarge()` | Create bill and release patient |
| `viewDoctors()` | Show doctor status |
| `findFreeDoctor()` | Locate available doctor by category |
| `removePatient()` | Remove discharged patient from list |

## 🔮 Future Enhancements

- **Database Integration**: Replace static arrays with SQL/NoSQL database
- **Persistent Storage**: Save patient and billing history
- **Advanced Scheduling**: Time slot management for doctors
- **Payment Gateway**: Integration with online payment systems
- **Prescription Management**: Store and manage prescriptions
- **Mobile App**: Android/iOS companion application
- **Multi-Hospital Support**: Manage multiple hospital branches
- **Staff Management**: Nurse, receptionist, admin roles
- **Insurance Integration**: Handle insurance claims
- **Analytics Dashboard**: Generate revenue and occupancy reports

## 📝 License

This project is part of Loki-Labs repository focusing on practical implementations of healthcare IT systems.

## 👨‍💻 Author

Created as an educational project demonstrating core concepts in:
- Object-Oriented Programming
- Data Structure Management
- Healthcare System Design
- Billing Logic Implementation

---

**Status**: Fully Functional ✅  
**Version**: 1.0  
**Last Updated**: May 2026