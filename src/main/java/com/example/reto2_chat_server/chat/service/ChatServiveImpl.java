package com.example.reto2_chat_server.chat.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.reto2_chat_server.chat.controller.UsersFromChatsPostRequest;
import com.example.reto2_chat_server.chat.repository.Chat;
import com.example.reto2_chat_server.chat.repository.ChatRepository;
import com.example.reto2_chat_server.chat.repository.ForeignKeysFromChatsDAO;
import com.example.reto2_chat_server.chat.repository.InsertUserChat;
import com.example.reto2_chat_server.chat.repository.MessageRepository;

import com.example.reto2_chat_server.chat.repository.UserChatsDAO;
import com.example.reto2_chat_server.chat.repository.UserInfo;
import com.example.reto2_chat_server.chat.repository.UserInfoDao;
import com.example.reto2_chat_server.chat.repository.UserChatRepository;
import com.example.reto2_chat_server.chat.repository.UsersFromChatDAO;
import com.example.reto2_chat_server.chat.repository.UsersFromChatRepository;
import com.example.reto2_chat_server.model.message.DataType;
import com.example.reto2_chat_server.model.message.Message;
import com.example.reto2_chat_server.model.message.MessageServiceModel;
import com.example.reto2_chat_server.security.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ChatServiveImpl implements ChatService {
	@Autowired
	private ChatRepository chatRepository;

    @Transactional
    public void deleteChatByIdWithTransaction(int id) {
        chatRepository.deleteChatById(id);
    }
    
   
    
    @Autowired
    private UserRepository userRepo;
	
	@Autowired
	private UserChatRepository userRepository;
	
	@Autowired
	private UsersFromChatRepository usersFromChatRepository;
	
	@Override
	public List<Integer> getChatsIdsByUserId(Integer userId) {
		List<UsersFromChatDAO> findByUserId = usersFromChatRepository.findByUser_Id(userId);
		List<Integer> result = new ArrayList<Integer>();
		
		for (UsersFromChatDAO usersFromChatDAO : findByUserId) {
			result.add(usersFromChatDAO.getChat().getId());
		}
		return result;
		
	}


	@Override
	public ChatServiceModel getChatsById(int id) {

	    Chat chatOptional = chatRepository.findById(id).orElseThrow(
	    		() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No encontrado")
	    		);
	    ChatServiceModel newChat = deDAOaService(chatOptional);
	    return newChat;
	}

	
	@Override
	public List<ChatServiceModel> getChats(int id) {
		
	    List<ChatServiceModel> response = new ArrayList<>();
	    List<Chat> chatList = new ArrayList<>();

	    Optional<UserChatsDAO> userChats = userRepository.findById(id);

	    if (userChats.isPresent()) {
	        List<UsersFromChatDAO> userChatsList = userChats.get().getChats();
	        List<Integer> chatIds = userChatsList.stream()
	                .map(userChat -> userChat.getChat().getId())
	                .collect(Collectors.toList());
	        chatList = (List<Chat>) chatRepository.findAllById(chatIds);
	        for (Chat chatDAO : chatList) {
	        	response.add(deDAOaService(chatDAO));
			}
	        
	    }
	    return response;
	}
	@Override
	public ResponseEntity<?> createChat(Chat chat, int userId) {
		try {
			 Date currentDate = new Date();
	         Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
	         chat.setCreatedat(currentTimestamp);
	         chat.setUpdatedat(currentTimestamp);
	         System.out.println(chat.isPublic());
			 if(!chat.isPublic() && !canCreate(userId)) {
				 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			 }
	         System.out.println("addsadas");
			 for (Chat chat1 : chatRepository.findAll()) {
				if(chat1.getName().equals(chat.getName())) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			System.out.println("111");
			chat = chatRepository.save(chat);
			ChatServiceModel response = new ChatServiceModel(chat.getId(), chat.isPublic(), chat.getName(),chat.getCreatedat(), chat.getUpdatedat());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar usuarios al chat.");

        }
		
		 

	}
	
	private boolean canCreate(int userId) {
		if(userRepo.canSend(userId)) {
			System.out.println("Can create");
			return true;
		}
		return false;
	}


	@Override
	public ResponseEntity<?> addUsersToChat(int chatId, List<UsersFromChatsPostRequest> usersToAdd, int userId, boolean created) {
		// TODO Auto-generated method stub
		 try {
			 if(!canAddPeple(chatId, userId) && created) {
				 if(!(usersToAdd.size() == 1 && usersToAdd.get(0).getUserId() == userId)) {
					 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);					 
				 }
			 }
			 System.out.println(chatId + " "+ usersToAdd.size() + " "+ userId);
			 List<UsersFromChatDAO> listDAO = new ArrayList<UsersFromChatDAO>(); 
			 
	            // Obtener el chat desde la base de datos
	            Chat chat = chatRepository.findById(chatId)
	                    .orElseThrow(() -> new EntityNotFoundException("Chat no encontrado con ID: " + chatId));
	            
	            Date currentDate = new Date();
	            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());


	            // Asignar el chat a cada usuario
	            for (UsersFromChatsPostRequest userFromChat : usersToAdd) {
	            	System.out.println(userFromChat.toString());
	            	ForeignKeysFromChatsDAO foreignKeysFromChatsDAO = new ForeignKeysFromChatsDAO(userFromChat.getChatId(), userFromChat.getUserId());
	            	
	            	UsersFromChatDAO usersFromChatDAO = new UsersFromChatDAO(foreignKeysFromChatsDAO, userFromChat.isAdmin());
	            	
	            	UserChatsDAO userChatsDAO = userRepository.findById(foreignKeysFromChatsDAO.getUserId())
	            	        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + foreignKeysFromChatsDAO.getUserId()));
	    
	            	/*usersFromChatDAO.setUser(userChatsDAO);
	            	usersFromChatDAO.setCreatedat(currentTimestamp);
	            	usersFromChatDAO.setUpdatedat(currentTimestamp);*/


	            	usersFromChatDAO.setChat(chat);
	            	//listDAO.add(usersFromChatDAO);
	            	System.out.println(userChatsDAO.getEmail());
	            	
	            	usersFromChatRepository.insert(userFromChat.getUserId(),
	            			userFromChat.getChatId(),
	            			userFromChat.isAdmin(),
	            			currentTimestamp,
	            			currentTimestamp);
	            }
	            
	            // Guardar todos los usuarios en el chat
	            //usersFromChatRepository.saveAll(listDAO);
	            
	            
	            
	            return new ResponseEntity<>(HttpStatus.OK);

	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

	        } catch (Exception e) {
	        	e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar usuarios al chat.");

	        }
	}


	@Override
	public ResponseEntity<?> removeUsersFromChat(int chatId, List<UsersFromChatsPostRequest> usersToRemove, int userId) {
	    try {
	    	
	    	if (!(usersToRemove.size() == 1 && usersToRemove.get(0).getUserId() == userId)) {
	    	    if(!canAddPeple(chatId, userId)) {
	    	    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	    }
	    	}

	    	
	        // Obtener el chat desde la base de datos
	        Chat chat = chatRepository.findById(chatId)
	                .orElseThrow(() -> new EntityNotFoundException("Chat no encontrado con ID: " + chatId));

	        // Eliminar los usuarios del chat
	        for (UsersFromChatsPostRequest userToRemove : usersToRemove) {
	            ForeignKeysFromChatsDAO foreignKeysFromChatsDAO = new ForeignKeysFromChatsDAO(userToRemove.getChatId(), userToRemove.getUserId());
	            UsersFromChatDAO usersFromChatDAO = new UsersFromChatDAO(foreignKeysFromChatsDAO, userToRemove.isAdmin());
	            

	            // Eliminar el usuario del chat
	            usersFromChatRepository.delete(usersFromChatDAO);
	        }

	        return new ResponseEntity<>(HttpStatus.OK);

	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar usuarios del chat.");
	    }
	}

	private boolean canAddPeple(int chatId, int userId) {
		if(userRepository.isAdminInChat(chatId, userId)) {
			return true;
		}
		return false;
	}


	@Override
	public ResponseEntity<?> deleteChatById(Integer id, int userChat) {
	    try {
	    	if(canDeleteInChat(id, userChat)) {
		        chatRepository.deleteById(id);
		        return new ResponseEntity(HttpStatus.NO_CONTENT);
	    	}else {
	    		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	    	} 
	    } catch (EmptyResultDataAccessException e) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT,"chat no encontrado");
	    }
	}




	private boolean canDeleteInChat(Integer id, int userChat) {
		for (UserInfoDao userId: userRepository.findAdminUsersInChat(id)) {
			System.out.println(userId + "aa" + userChat);
			if(userChat == userId.getUserId()) {
				return true;
			}
		}
		return false;
	}


	public List<Integer> getChatIds(int id) {
		List<Integer> response = new ArrayList<>();

		Optional<UserChatsDAO> userChats = userRepository.findById(id);

		if (userChats.isPresent()) {
			List<UsersFromChatDAO> userChatsList = userChats.get().getChats();
			response = userChatsList.stream()
					.map(userChat -> userChat.getChat().getId())
					.collect(Collectors.toList());
		}

		return response;
	}

	private ChatServiceModel deDAOaService(Chat chatDAO) {
		ChatServiceModel chat = new ChatServiceModel(
				chatDAO.getId(),
				chatDAO.isPublic(),
				chatDAO.getName(),
				chatDAO.getCreatedat(),
				chatDAO.getUpdatedat(),
				deDAOaServiceMessages(chatDAO.getMessages()),
				deDAOaServiceUsers(chatDAO.getUsers())				
				);
		return chat;
	}
	private List<MessageServiceModel> deDAOaServiceMessages(List<Message> messages) {
		List<MessageServiceModel> response = new ArrayList<MessageServiceModel>();
		for (Message messageDAO : messages) {
			if(messageDAO.getDataType() == DataType.IMAGE) {
				messageDAO.setContent(getImageUser(messageDAO.getContent()));
			}
			
			
			MessageServiceModel messageService = new MessageServiceModel(
					messageDAO.getId(),
					messageDAO.getDataType(),
					messageDAO.getContent(),
					messageDAO.getCreatedAt(),
					messageDAO.getUpdatedAt(),
					messageDAO.getUserId().convertFromDAOtoServiceResumedForMessages(messageDAO.getUserId()));


			response.add(messageService);
		}
		return response;
	}

	private String getImageUser(String content) {
		try {
			File file = new File(content);
			byte[] fileContent;
			fileContent = Files.readAllBytes(file.toPath());
			return Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	private UserChatServiceModel deDAOaServiceUser(UserChatsDAO user) {
		UserChatServiceModel serviceUser = new UserChatServiceModel(
				user.getId(),
				user.getName(),
				user.getEmail()

				);
		return serviceUser;
	}

	private List<UsersFromChatServiceModel> deDAOaServiceUsers(List<UsersFromChatDAO> users) {
		List<UsersFromChatServiceModel> listUserService = new ArrayList<UsersFromChatServiceModel>();
		for (UsersFromChatDAO user_ChatDAO : users) {
			UsersFromChatServiceModel userService = new UsersFromChatServiceModel(
					deDAOaServiceId(user_ChatDAO.getId()),
					deDAOaServiceUser(user_ChatDAO.getUser()),
					user_ChatDAO.isAdmin()				
					);
			listUserService.add(userService);
		}

		return listUserService;
	}

	private ForeignKeysFromChatsService deDAOaServiceId(ForeignKeysFromChatsDAO id) {
		ForeignKeysFromChatsService idService = new ForeignKeysFromChatsService(
				id.getChatId(),
				id.getUserId()
				);		
		return idService;
	}



	@Override
	public ResponseEntity<?> getUserNotInChat(int chatId) {
	    try {
	    	
	    	Optional<Chat> chat = chatRepository.findById(chatId);
		    List<UserChatsDAO> allUsers = userRepository.findAllByOrderByEmail();
		    List<String> emailsInChat = userRepository.findEmailsInChat(chatId);
		    List<String> result = new ArrayList<String>();
	    	if(!chat.get().isPublic()){
	    		List<String> listOfAllAlumnos = userRepo.findEmailsByRoleId();	
	    		for (String string : listOfAllAlumnos) {
	    			result.add(string);
				}
	    		for (String string2 : emailsInChat) {
	    			result.add(string2);
				}
	    	}
	        

	        List<UserInfo> usersNotInChat = new ArrayList<>();

	        for (UserChatsDAO user : allUsers) {
	            if (!result.contains(user.getEmail())) {
	                UserInfo userInfo = new UserInfo(user.getEmail(), user.getId());
	                usersNotInChat.add(userInfo);
	            }
	        }

	        return ResponseEntity.ok(usersNotInChat);
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "Chat not found");
	    }
	}

	@Override
	public ResponseEntity<?> getUserInChat(int chatId, int userId) {
		 try {
			 List<UserInfoDao> usersInChat = userRepository.findNonAdminUsersInChat(chatId, userId);	
			 List<UserInfo> usersInChatInto = new ArrayList<>();
			 for (UserInfoDao userInfo : usersInChat) {
				UserInfo user = new UserInfo(
						userInfo.getEmail(),
						userInfo.getUserId()						
						);
				usersInChatInto.add(user);
			}
			 return ResponseEntity.ok(usersInChatInto);
		} catch (Exception e) {
			System.out.println(e.getMessage());
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "Chat not found");
	    }
	}


	@Override
	public ResponseEntity<?> getPublicChats(int id) {
		try {

			List<ChatShow> listOfChatsUser = chatRepository.findAllUserChatPublic(id);
			
			List<ChatShow> listOfChatsPublic = chatRepository.findAllChatPublic(id);
			List<ChatShowResponse> respone = new ArrayList<ChatShowResponse>();
			
			for (ChatShow chatShow : listOfChatsPublic) {
				boolean i = false;
				for (ChatShow chatShowUser : listOfChatsUser) {
					if(chatShow.getChatId() == chatShowUser.getChatId()) {
						i = true;
						break;
					}
				}
				if(!i) {
					respone.add(new ChatShowResponse(chatShow.getChatId(), chatShow.getName()));
				}
				
			}
			System.out.println(respone.toString());
			return ResponseEntity.ok(respone);
		} catch (Exception e) {
			System.out.println(e.getMessage());
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "Chat not found");
	    }
	}
}
