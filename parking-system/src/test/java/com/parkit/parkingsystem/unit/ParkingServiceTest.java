package com.parkit.parkingsystem.unit;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Date;

import static com.parkit.parkingsystem.constants.ParkingType.CAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest() {
    	  try {

              parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
          } catch (Exception e) {
              e.printStackTrace();
              throw  new RuntimeException("Failed to set up test mock objects");
          }}
    
    @Test
    public void processIncomingVehicleCarTypeTest() throws Exception {
        when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
        when(inputReaderUtil.readSelection()).thenReturn(1);
        parkingService.processIncomingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
    }

    @Test
    public void processIncomingVehicleBikeTypeTest() throws Exception {
        when(parkingSpotDAO.getNextAvailableSlot((any(ParkingType.class)))).thenReturn(1);
        when(inputReaderUtil.readSelection()).thenReturn(2);
        parkingService.processIncomingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
    }

    @Test
    public void processIncomingVehicle_Error_Number_vehicule() throws Exception{
        when(parkingSpotDAO.getNextAvailableSlot((any(ParkingType.class)))).thenReturn(0);
        when(inputReaderUtil.readSelection()).thenReturn(1);
        parkingService.processIncomingVehicle();
        verify(parkingSpotDAO, times(1)).getNextAvailableSlot(any(ParkingType.class));
    }
    @Test
    public void processExitingVehicleTest() throws Exception {
    	 parkingService.processExitingVehicle();
         verify(parkingSpotDAO, Mockito.times(0)).updateParking(any(ParkingSpot.class));
     }

}





