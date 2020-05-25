package com.pmt.mattress;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.pmt.mattress.mattress.Spring;
import com.pmt.mattress.rest.Foam;

public class DBAccess {
	private  String dbPath;//remove static
	private  String username;
	private  String password;//char array
	
	public DBAccess() {
		
	}
	
	public void DBA_init() {
		readPropertiesFile();
	}
		
	public void readPropertiesFile() {
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("/Users/zzh/eclipse-workspace/MyProject/src/com/pmt/mattress/config.properties");
			prop.load(input);
			dbPath = prop.getProperty("dbPath");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
/************************************************************ Mattress ***********************************************************/	
	
	public int getMattressID (String name) {
		int ID = 0;
		Connection myConn = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			final PreparedStatement myStmt = myConn.prepareStatement("SELECT Mattress_ID FROM MATTRESSES WHERE Mattress_Name = ?");
			myStmt.setString(1,name);
			final ResultSet myRs = myStmt.executeQuery();
			myRs.next();
			ID = myRs.getInt("Mattress_ID");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ID;
	}
	
	public void createMattress(Mattress m) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("INSERT INTO MATTRESSES (Mattress_Name,) VALUES (?)");
			myStmt.setString(1,m.getName());
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
			
	}
	
	public Mattress getMattress(int id) { 
		final Mattress m = new Mattress();
		Connection myConn = null;
		try {
			Double height = 0.0;
			Double twinPrice = 0.0;
			Double txlPrice = 0.0;
			Double fullPrice = 0.0;
			Double queenPrice = 0.0;
			Double kingPrice = 0.0;
			Double ckingPrice = 0.0;
			ArrayList<Layer> layers = new ArrayList<Layer>();
			myConn = DriverManager.getConnection(dbPath,username,password);
			PreparedStatement myStmt1 = myConn.prepareStatement("SELECT * FROM MATTRESSES WHERE MATTRESS_ID = ?");
			myStmt1.setInt(1, id);
			ResultSet myRs1 = myStmt1.executeQuery();
			myRs1.next();
			m.setName(myRs1.getString("Mattress_Name"));
			m.setID(myRs1.getInt("Mattress_ID"));
			PreparedStatement myStmt2 = myConn.prepareStatement("SELECT * FROM MATTRESS_LAYERS WHERE MATTRESS_ID = ?");
			myStmt2.setInt(1, id);
			ResultSet myRs2 = myStmt2.executeQuery();
			while(myRs2.next()) {
				Layer l = new Layer();
				l = getLayer(myRs2.getInt("Layer_ID"));
				height = height + l.getThickness();
				twinPrice = twinPrice + l.getPriceTwin();
				txlPrice = txlPrice + l.getPriceTxl();
				fullPrice = fullPrice + l.getPriceFull();
				queenPrice = queenPrice + l.getPriceFull();
				kingPrice = kingPrice + l.getPriceKing();
				ckingPrice = ckingPrice + l.getPriceCking();
				layers.add(l);
			}
			m.setPriceTwin(twinPrice);
			m.setPriceTxl(txlPrice);
			m.setPriceFull(fullPrice);
			m.setPriceQueen(queenPrice);
			m.setPriceKing(kingPrice);
			m.setPriceCking(ckingPrice);
			m.setLayers(layers);
			m.setHeight(height);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}
	
	public ArrayList<Mattress> getAllMattresses() {
		ArrayList<Mattress> allMattresses = new ArrayList<Mattress>();
		Connection myConn = null;
	    try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			final PreparedStatement myStmt = myConn.prepareStatement("SELECT Mattress_ID FROM MATTRESSES");
			final ResultSet myRs = myStmt.executeQuery();
			while(myRs.next()) {
				Mattress m = getMattress(myRs.getInt("Mattress_ID"));
				allMattresses.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allMattresses;	
	}
	
	public void addLayer(int mId, int lId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("INSERT INTO MATTRESS_LAYERS (MATTRESS_ID, LAYER_ID) VALUES (?,?)");
			myStmt.setInt(1, mId);
			myStmt.setInt(2, lId);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();//Handle the case which mattress_id or layer_id doesn't not exist in database
		} finally {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	/*public ArrayList<Layer> getLayers(int mId) {
		ArrayList<Layer> alL = new ArrayList<Layer>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("SELECT foam_name,Thickness, bf_price from mattress_layers MF inner join layers L ON L.Layer_ID = MF.layer_ID inner join foams F ON L.Foam_ID = F.Foam_ID WHERE Mattress_id = ?");
			myStmt.setInt(1, mId);
			myRs = myStmt.executeQuery();
			while(myRs.next()) {
				Layer l = new Layer();
				l.setName(myRs.getString("foam_name"));
				l.setThickness(myRs.getDouble("Thickness"));
				l.setBfPrice(myRs.getDouble("bf_price"));
				l.setLayerPrices();
				alL.add(l);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return alL;
	}*/
	
	/*public int getLayerID(Layer l) {
		int id = 0;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("Select F.Foam_ID from foams F inner join Layers L on F.Foam_ID = L.Foam_ID Where Foam_Name = ? and Thickness = ?");
			myStmt.setString(1, l.getName());
			myStmt.setDouble(2, l.getThickness());
			myRs = myStmt.executeQuery();
			myRs.next();
			id = myRs.getInt("Foam_ID");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return id;
	}*/
	
	
	
	
	public void deleteMattress(int id) {
		Connection myConn = null;
		PreparedStatement myStmt1 = null;
		PreparedStatement myStmt2 = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt1 = myConn.prepareStatement("DELETE FROM MATTRESS_LAYERS WHERE MATTRESS_ID = ?");
			myStmt1.setInt(1, id);
			myStmt1.executeUpdate();
			myStmt2 = myConn.prepareStatement("DELETE FROM MATTRESSES WHERE Mattress_ID = ?");
			myStmt2.setInt(1, id);
			myStmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt1,myRs);
		}
	}
	
	public void saveMattressPrice(HashMap <MattressSize, Double> map) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs= null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("INSERT INTO MATTRESSES(Twin, Txl, Full, Queen, King, Cking) VALUES (?,?, ?, ?, ?, ?, ?)");
			myStmt.setDouble(1, map.get(MattressSize.TWIN));
			myStmt.setDouble(2, map.get(MattressSize.TWINXL));
			myStmt.setDouble(3, map.get(MattressSize.FULL));
			myStmt.setDouble(4, map.get(MattressSize.QUEEN));
			myStmt.setDouble(5, map.get(MattressSize.KING));
			myStmt.setDouble(6, map.get(MattressSize.CKING));
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
	}
	public double getFoamPrice(String name) {
		double returnValue = 0;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("select BF_Price from FOAMS where foam_name = ?");
			myStmt.setString(1, name);
			myRs = myStmt.executeQuery();
			myRs.next();
			returnValue = myRs.getDouble("BF_Price");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return returnValue;
	}
	
	public HashMap<MattressSize, Double> getOutercoverPrice(String name) {
		HashMap<MattressSize, Double> map = new HashMap<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("select Twin,Txl,Full,Queen,King,Cking from OUTER_COVERS where Cover_name = ?");
			myStmt.setString(1, name);
			myRs = myStmt.executeQuery();
			myRs.next();
			map.put(MattressSize.TWIN, myRs.getDouble("Twin"));
			map.put(MattressSize.TWINXL, myRs.getDouble("Txl"));
			map.put(MattressSize.FULL, myRs.getDouble("Full"));
			map.put(MattressSize.QUEEN, myRs.getDouble("Queen"));
			map.put(MattressSize.KING, myRs.getDouble("King"));
			map.put(MattressSize.CKING, myRs.getDouble("Cking"));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		
		return map;
	}
/************************************************************ Connection Close ***********************************************************/	
	
	public void connectionClose(Connection conn, PreparedStatement ps, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch(SQLException e) { e.printStackTrace();}
		}
		if(ps != null) {
			try {
				ps.close();
			} catch(SQLException e) { e.printStackTrace();}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) { e.printStackTrace();}
		}
	}
	
	public void connectionClose(Connection conn, PreparedStatement ps) {
		if(ps != null) {
			try {
				ps.close();
			} catch(SQLException e) { e.printStackTrace();}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) { e.printStackTrace();}
		}
	}
	
/************************************************************ Layer ***********************************************************/
	
	public int getLayerID(String name) {
		int id = 0;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("SELECT Layer_ID from Layers WHERE Layer_Name =  ?");
			myStmt.setString(1,name);
			myRs = myStmt.executeQuery();
			myRs.next();
			id = myRs.getInt("Layer_ID");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return id;
	}
	
	public Layer getLayer(final int id) {
		final Layer l = new Layer();
		Connection myConn = null;
	    try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			final PreparedStatement myStmt = myConn.prepareStatement("SELECT * FROM Layers WHERE LAYER_ID = ?");// * -> all column
			myStmt.setInt(1, id);
			final ResultSet myRs = myStmt.executeQuery();
			myRs.next();
			int material_ID = 0;
			l.setID(myRs.getInt("Layer_ID"));
			l.setName(myRs.getString("Layer_Name"));
			l.setThickness(myRs.getDouble("Thickness"));
			material_ID = myRs.getInt("Foam_ID");
			if(myRs.wasNull()) {
				l.setSpring(getSpring((myRs.getInt("Spring_ID"))));
				l.setPriceTwin(l.getSpring().getPriceTwin());
				l.setPriceTxl(l.getSpring().getPriceTxl());
				l.setPriceFull(l.getSpring().getPriceFull());
				l.setPriceQueen(l.getSpring().getPriceQueen());
				l.setPriceKing(l.getSpring().getPriceKing());
				l.setPriceCking(l.getSpring().getPriceCking());
			} else {
				l.setFoam(getFoam(material_ID));
				l.setPriceTwin(l.getFoam().getBfPrice()*l.getThickness()*38*74/(12*12));
				l.setPriceTxl(l.getFoam().getBfPrice()*l.getThickness()*38*80/(12*12));
				l.setPriceFull(l.getFoam().getBfPrice()*l.getThickness()*54*74/(12*12));
				l.setPriceQueen(l.getFoam().getBfPrice()*l.getThickness()*60*80/(12*12));
				l.setPriceKing(l.getFoam().getBfPrice()*l.getThickness()*76*80/(12*12));
				l.setPriceCking(l.getFoam().getBfPrice()*l.getThickness()*72*84/(12*12));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return l;
	}
	
	public List<Layer> getAllLayers() {
		ArrayList<Layer> allLayers = new ArrayList<Layer>();
		Connection myConn = null;
	    try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			final PreparedStatement myStmt = myConn.prepareStatement("SELECT * FROM Layers");
			final ResultSet myRs = myStmt.executeQuery();
			while(myRs.next()) {
				Layer l = new Layer();
				int material_ID = 0;
				l.setID(myRs.getInt("Layer_ID"));
				l.setName(myRs.getString("Layer_Name"));
				l.setThickness(myRs.getDouble("Thickness"));
				material_ID = myRs.getInt("Foam_ID");
				if(myRs.wasNull()) {
					l.setSpring(getSpring((myRs.getInt("Spring_ID"))));
					l.setPriceTwin(l.getSpring().getPriceTwin());
					l.setPriceTxl(l.getSpring().getPriceTxl());
					l.setPriceFull(l.getSpring().getPriceFull());
					l.setPriceQueen(l.getSpring().getPriceQueen());
					l.setPriceKing(l.getSpring().getPriceKing());
					l.setPriceCking(l.getSpring().getPriceCking());
				} else {
					l.setFoam(getFoam(material_ID));
					l.setPriceTwin(l.getFoam().getBfPrice()*l.getThickness()*38*74/(12*12));
					l.setPriceTxl(l.getFoam().getBfPrice()*l.getThickness()*38*80/(12*12));
					l.setPriceFull(l.getFoam().getBfPrice()*l.getThickness()*54*74/(12*12));
					l.setPriceQueen(l.getFoam().getBfPrice()*l.getThickness()*60*80/(12*12));
					l.setPriceKing(l.getFoam().getBfPrice()*l.getThickness()*76*80/(12*12));
					l.setPriceCking(l.getFoam().getBfPrice()*l.getThickness()*72*84/(12*12));
				}
				allLayers.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return allLayers;
	}
	
	public void createLayerByFoam(Layer l) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("INSERT INTO Layers (Layer_Name,Foam_ID,Thickness) VALUES (?,?,?)");
			myStmt.setString(1,l.getName());
			myStmt.setInt(2,getFoamID(l.getFoam().getName()));
			myStmt.setDouble(3, l.getThickness());
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void createLayerBySpring(Layer l) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("INSERT INTO Layers (Layer_Name,Spring_ID,Thickness) VALUES (?,?,?)");
			myStmt.setString(1,l.getName());
			myStmt.setInt(2,getSpringID(l.getSpring().getName()));
			myStmt.setDouble(3,l.getThickness());
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void deleteLayer(int id) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("DELETE FROM Layers WHERE Layer_ID = ?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void updateLayerName(int id, String newName) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Layers SET Layer_Name = ? WHERE Layer_ID = ?; ");
			myStmt.setString(1, newName);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
		
	}
/************************************************************ SPRING ***********************************************************/
	
	public int getSpringID(String name) {
		int id = 0;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("SELECT Spring_ID FROM SPRINGS WHERE Spring_Name =  ?");
			myStmt.setString(1,name);
			myRs = myStmt.executeQuery();
			myRs.next();
			id = myRs.getInt("Spring_ID");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return id;
	}
	
	public Spring getSpring(int id) {
		Spring s = new Spring();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("SELECT * From SPRINGS WHERE Spring_ID =  ?");
			myStmt.setInt(1,id);
			myRs = myStmt.executeQuery();
			myRs.next();
			s.setID(myRs.getInt("Spring_ID"));
			s.setName(myRs.getString("Spring_Name"));
			s.setPriceTwin(myRs.getDouble("Twin_Price"));
			s.setPriceTxl(myRs.getDouble("Txl_Price"));
			s.setPriceFull(myRs.getDouble("Full_Price"));
			s.setPriceQueen(myRs.getDouble("Queen_Price"));
			s.setPriceKing(myRs.getDouble("King_Price"));
			s.setPriceCking(myRs.getDouble("Cking_Price"));
			s.setThickness(myRs.getDouble("Thickness"));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return s;
	}
	
	public List<Spring> getAllSprings() {
		ArrayList<Spring> allSprings = new ArrayList<Spring>();
		Connection myConn = null;
		PreparedStatement myStmt  = null;
		ResultSet myRs = null;
	    try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("SELECT * FROM SPRINGS");
			myRs = myStmt.executeQuery();
			while(myRs.next()) {
				Spring s = new Spring();
				s.setID(myRs.getInt("Spring_ID"));
				s.setName(myRs.getString("Spring_Name"));
				s.setPriceTwin(myRs.getDouble("Twin_Price"));
				s.setPriceTxl(myRs.getDouble("Txl_Price"));
				s.setPriceFull(myRs.getDouble("Full_Price"));
				s.setPriceQueen(myRs.getDouble("Queen_Price"));
				s.setPriceKing(myRs.getDouble("King_Price"));
				s.setPriceCking(myRs.getDouble("Cking_Price"));
				s.setThickness(myRs.getDouble("Thickness"));
				allSprings.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionClose(myConn, myStmt,myRs);
		}	
		return allSprings;
		
	}
	
	public void createSpring(Spring s) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("INSERT INTO SPRINGS (Spring_Name, Thickness, Twin_Price, Txl_Price, Full_Price, Queen_Price,King_Price, Cking_Price) VALUES (?,?,?,?,?,?,?,?)");
			myStmt.setString(1, s.getName());
			myStmt.setDouble(2, s.getThickness());
			myStmt.setDouble(3, s.getPriceTwin());
			myStmt.setDouble(4, s.getPriceTxl());
			myStmt.setDouble(5, s.getPriceFull());
			myStmt.setDouble(6, s.getPriceQueen());
			myStmt.setDouble(7, s.getPriceKing());
			myStmt.setDouble(8, s.getPriceCking());
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}

	public void deleteSpring(int id) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("DELETE FROM SPRINGS WHERE Spring_ID = ?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void updateSpringName(int id, String newName) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE SPRINGS SET Spring_Name = ? WHERE Spring_ID = ?; ");
			myStmt.setString(1, newName);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
		
	}
	
	public void updateSpringTwinPrice(int id, Double newPrice) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Springs SET Twin_Price = ? WHERE Spring_ID = ?; ");
			myStmt.setDouble(1, newPrice);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void updateSpringTxlPrice(int id, Double newPrice) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Springs SET Txl_Price = ? WHERE Spring_ID = ?; ");
			myStmt.setDouble(1, newPrice);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void updateSpringFullPrice(int id, Double newPrice) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Springs SET Full_Price = ? WHERE Spring_ID = ?; ");
			myStmt.setDouble(1, newPrice);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void updateSpringQueenPrice(int id, Double newPrice) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Springs SET Queen_Price = ? WHERE Spring_ID = ?; ");
			myStmt.setDouble(1, newPrice);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void updateSpringKingPrice(int id, Double newPrice) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Springs SET King_Price = ? WHERE Spring_ID = ?; ");
			myStmt.setDouble(1, newPrice);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
	public void updateSpringCkingPrice(int id, Double newPrice) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Springs SET Cking_Price = ? WHERE Spring_ID = ?; ");
			myStmt.setDouble(1, newPrice);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}
	
/************************************************************ FOAM ***********************************************************/
	
	public int getFoamID(String name) {
		int id = 0;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("SELECT Foam_ID FROM Foams WHERE Foam_Name =  ?");
			myStmt.setString(1,name);
			myRs = myStmt.executeQuery();
			myRs.next();
			id = myRs.getInt("Foam_ID");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return id;
	}
	
	public Foam getFoam(int id) {
		Foam f = new Foam();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("SELECT * FROM Foams WHERE Foam_ID =  ?");
			myStmt.setInt(1,id);
			myRs = myStmt.executeQuery();
			myRs.next();
			f.setID(myRs.getInt("Foam_ID"));
			f.setName(myRs.getString("Foam_Name"));
			f.setBfPrice(myRs.getDouble("BF_Price"));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return f;
	}
	
	public List<Foam> getAllFoams() {
		ArrayList<Foam> allFoams = new ArrayList<Foam>();
		Connection myConn = null;
		PreparedStatement myStmt  = null;
		ResultSet myRs = null;
	    try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("SELECT * FROM Foams");
			myRs = myStmt.executeQuery();
			while(myRs.next()) {
				Foam f = new Foam();
				f.setID(myRs.getInt("Foam_ID"));
				f.setName(myRs.getString("Foam_Name")) ;
				f.setBfPrice(myRs.getDouble("BF_Price"));
				allFoams.add(f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionClose(myConn, myStmt,myRs);
		}	
		return allFoams;
		
	}
	
	public void createFoam(Foam f) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("INSERT INTO FOAMS (Foam_Name, BF_Price) VALUES (?,?)");
			myStmt.setString(1, f.getName());
			myStmt.setDouble(2, f.getBfPrice());
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}

	public void deleteFoam(int id) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("DELETE FROM Foams WHERE Foam_ID = ?");
			myStmt.setInt(1, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}

	public void updateFoamName(int id, String newName) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Foams SET Foam_Name = ? WHERE Foam_ID = ?; ");
			myStmt.setString(1, newName);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
		
	}

	public void updateFoamPrice(int id, Double newPrice) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = DriverManager.getConnection(dbPath,username,password);
			myStmt = myConn.prepareStatement("UPDATE Foams SET BF_Price = ? WHERE Foam_ID = ?; ");
			myStmt.setDouble(1, newPrice);
			myStmt.setInt(2, id);
			myStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt);
		}
	}

	

}	
	

