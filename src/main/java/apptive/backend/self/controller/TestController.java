package apptive.backend.self.controller;

import apptive.backend.self.dto.BlueDto;
import apptive.backend.self.dto.BurnDto;
import apptive.backend.self.dto.StressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/blue") // 게시글 단일 조회
    public ResponseEntity<String> getBlue(BlueDto blueDto) {
        int[] blue = blueDto.getQ(); // 우울증 테스트 값 배열
        int sum = 0;
        for(int i=0; i<blue.length; i++) {
            sum += blue[i];
        }
        String result;
        if(sum<5)
            result = "당신의 정신은 건강한 상태입니다!";
        else {
            result = "최근 2주 간 우울 증상이 다수 발견되었습니다. 정신건강 전문의나 지역 정신보건기관의 상담사와 같은 전문가에게 도움을 구해보세요.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/burn") // 게시글 단일 조회
    public ResponseEntity<String> getBurn(BurnDto burnDto) {
        int[] burn = burnDto.getQ();
        int sum = 0;
        for(int i=0; i<burn.length; i++) {
            sum += burn[i];
        }
        String result;
        if(sum<65)
            result = "번아웃 증후군이 아닌 것으로 예상됩니다. 하지만, 번아웃 증후군 예방을 위해 스트레스 관리를 잘 하시고, 적절한 휴식과 취미 생활을 즐기는 것을 추천합니다.";
        else {
            result = "번아웃 증후군이 의심됩니다. 업무량을 줄이고, 휴식이나 취미를 통해 스트레스를 해소할 시간을 갖는 것을 추천합니다. 혼자 극복이 힘들다면 전문가의 도움을 받는 것이 좋습니다.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/stress") // 게시글 단일 조회
    public ResponseEntity<String> getStress(StressDto stressDto) {
        int[] stress = stressDto.getQ(); // 우울증 테스트 값 배열
        int sum = 0;
        for(int i=0; i<stress.length; i++) {
            sum += stress[i];
        }
        String result;
        if(sum>=5 && sum<=23)
            result = "스트레스가 적절한 수준으로, 심리적으로 안정된 상태입니다.";
        else if(sum>=24 && sum<=26) {
            result = "스트레스로 인해 부정적인 영향을 받고 있는 상태로, 스트레스를 관리할 필요가 있습니다. 스트레스를 해소하기 위한 적극적인 노력이 필요합니다.";
        }
        else {
            result = "극심한 스트레스를 받고 있는 경우로, 일상생활의 어려움을 겪고 있을 것으로 판단되는 상태입니다. 정신건강 전문의나 전문상담사의 도움을 받을 것을 적극 권장합니다.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }
}
