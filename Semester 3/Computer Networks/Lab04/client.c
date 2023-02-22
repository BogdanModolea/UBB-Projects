#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <string.h>
#include <unistd.h>

int main(){
	uint16_t port = 1234;
	char address[17] = "172.30.118.216";

	int sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if(sockfd < 0){
		perror("Socket error!");
		return -1;
	}
	printf("Socket create successfully\n");

	struct sockaddr_in server_address;
	server_address.sin_family = AF_INET;
	server_address.sin_port = htons(port);
	inet_aton(address, &server_address.sin_addr);
	socklen_t server_address_len = sizeof(server_address);

	int res = connect(sockfd, (const struct sockaddr*) &server_address, server_address_len);
	if(res < 0){
		perror("Conection error");
		return -1;
	}
	printf("Successfully connected to server %s on port %d\n", address, port);

	char message[101] =  "Ana are prune mari\0";
	int size = strlen(message);
	size = htonl(size);
	write(sockfd, &size, sizeof(int));
	write(sockfd, message, strlen(message) + 1);
	return 0;
}
