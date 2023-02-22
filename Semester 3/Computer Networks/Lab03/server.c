#include <stdio.h>
#include <netinet/ip.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/types.h>

int main(){
	uint16_t port = 1234;
	char address[256] = "172.30.115.77";
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
		// To be continues with Gabi
	}
	return 0;	
}