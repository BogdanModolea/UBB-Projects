#include <stdio.h>
#include <netinet/ip.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <unistd.h>

void serve_client(int client_fd){
	int size;
	read(client_fd, &size, sizeof(int));
	size = ntohl(size);
	printf("Client said %d\n", size);

	char message[size + 1];
	for(int i = 0; i < size + 1; i++)
		message[i] = '\0';
	read(client_fd, message, size);
	printf("Client said %s\n", message);
}

int main(){
	uint16_t port = 1234;
	char address[256] = "172.30.118.216";
	int sock_fd = socket(AF_INET, SOCK_STREAM, 0);
	int backlog = 7;

	if(sock_fd == -1){
		perror("Socket error");
		return -1;
	}
	printf("Socket created successfully\n");
	struct sockaddr_in socket_address;
	socket_address.sin_family = AF_INET;
	socket_address.sin_port = htons(port);
	inet_aton(address, &socket_address.sin_addr);
	socklen_t socket_address_len = sizeof(socket_address);

	int res = bind(sock_fd, (const struct sockaddr*)&socket_address, socket_address_len);
	if(res == -1){
		perror("Bind error");
		return -1;
	}
	printf("Binded to ip %s on port %hu\n", address, port);
	res = listen(sock_fd, backlog);
	if(res == -1){
		perror("Listen error");
		return -1;
	}
	printf("Listen succeeded\n");
	while(1){
		struct sockaddr_in client_address;
		socklen_t client_address_len;
		int client_fd = accept(sock_fd, (struct sockaddr*)&client_address, &client_address_len);

		if(client_fd == -1){
			perror("Accept error");
			continue;
		}

		printf("Client with ip %s and port %d connected successfully\n", inet_ntoa(client_address.sin_addr), ntohs(client_address.sin_port));

		serve_client(client_fd);
	}
	return 0;
}
