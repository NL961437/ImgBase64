mvn clean package
docker image build -t img-base-64:latest .
docker tag img-base-64 192.168.1.145:5000/img-base-64
docker push 192.168.1.145:5000/img-base-64